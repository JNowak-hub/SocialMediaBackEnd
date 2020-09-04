package pl.surf.web.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.surf.web.demo.model.Event;
import pl.surf.web.demo.model.EventFavaouritePK;
import pl.surf.web.demo.model.FavouriteEvent;
import pl.surf.web.demo.model.UserEvent;

import java.util.List;
import java.util.Optional;

public interface FavouriteEventRepo extends JpaRepository<FavouriteEvent, EventFavaouritePK> {

    @Query(value = "SELECT * FROM FAVOURITE_EVENT WHERE event_event_id=?1 AND user_app_user_id=?2"
            , nativeQuery = true)
    FavouriteEvent findFavoriteEvent(Long eventId, Long userId);

}
