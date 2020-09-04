package pl.surf.web.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.surf.web.demo.model.Event;
import pl.surf.web.demo.model.UserEvent;
import pl.surf.web.demo.model.responses.EventResponses;

import java.util.List;
import java.util.Optional;


public interface EventRepo extends JpaRepository<Event, Long> {

    @Query("select e from Event e where e.date >= CURDATE()")
    List<Event> getCurrentEvents();

    @Query(value = "SELECT * FROM USERS_EVENT WHERE event_id=?1 AND user_id=?2"
            , nativeQuery = true)
    List<Optional<UserEvent>> findUserEventByIds(Long eventId, Long userId);

    @Query(value = "SELECT EXISTS(SELECT fe.event_event_id   FROM FAVOURITE_EVENT fe WHERE event_event_id=?1 AND USER_APP_USER_ID=?2)"
            , nativeQuery = true)
    boolean checkIfUserAlreadyFavouriteEvent(Long eventId, Long userId);

    @Modifying
    @Query(value = "DELETE FROM USERS_EVENT WHERE event_id=?1 AND user_id=?2"
            , nativeQuery = true)
    void delete(Long eventId, Long userId);

    @Modifying
    @Query(value = "DELETE FROM FAVOURITE_EVENT WHERE event_id=?1 AND user_id=?2"
            , nativeQuery = true)
    void deleteFavouriteEvent(Long eventId, Long userId);

    @Query(value = "SELECT u.email FROM USERS_EVENT as ue INNER JOIN USERS as u ON ue.USER_ID = u.USER_ID where EVENT_ID =?1", nativeQuery = true)
    List<String> getEmailsOfSignedUsersToEvent(Long emailId);

   @Query(value = "SELECT u.level_id as lv,fe.user_id as userId, e.* FROM EVENTS as e left JOIN FAVOURITE_EVENT AS fe ON fe.event_id = e.event_id left \n" +
           "           JOIN USERS AS u ON u.user_id = fe.user_id where u.user_id =1 or u.user_id is null", nativeQuery = true)
    List<EventResponses> getFavouriteEvents(Long userId);


}
