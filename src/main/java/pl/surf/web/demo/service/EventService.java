package pl.surf.web.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.surf.web.demo.configuration.JwtUtil;
import pl.surf.web.demo.exceptions.EventIsFullException;
import pl.surf.web.demo.exceptions.EventNotFoundException;
import pl.surf.web.demo.exceptions.UserAlreadyAssigned;
import pl.surf.web.demo.exceptions.UserNotFoundException;
import pl.surf.web.demo.model.Event;
import pl.surf.web.demo.model.FavouriteEvent;
import pl.surf.web.demo.model.requests.EventRequest;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.model.responses.EventResponses;
import pl.surf.web.demo.repository.EventRepo;
import pl.surf.web.demo.repository.FavouriteEventRepo;
import pl.surf.web.demo.repository.UserRepo;
import pl.surf.web.demo.util.EventMapper;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    private EventRepo eventRepo;
    private UserRepo userRepo;
    private JwtUtil util;
    private EventMapper mapper;
    private FavouriteEventRepo favouriteEventRepo;
    private MailService mailService;

    public EventService(EventRepo eventRepo, UserRepo userRepo, JwtUtil util, EventMapper mapper, FavouriteEventRepo favouriteEvent, MailService mailService) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.util = util;
        this.mapper = mapper;
        this.favouriteEventRepo = favouriteEvent;
        this.mailService = mailService;
    }

    @Transactional
    public Event addEvent(Event event) {
        return eventRepo.save(event);

    }

    public List<Event> getCurrentEvents() {
        return eventRepo.getCurrentEvents();
    }

    public List<Event> getCurrentEvents2() {
        List<Event> events = eventRepo.findAll().stream()
                .filter(d -> d.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
        return events;
    }


    @Transactional
    public boolean takePartInEvent(HttpServletRequest request, Long eventId) {
        final Long userId = util.getUserIdFromRequest(request);
        final Optional<UserApp> optionalUserApp = userRepo.findById(userId);
        final Optional<Event> optionalEvent = eventRepo.findById(eventId);

        if (!optionalUserApp.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        if (!optionalEvent.isPresent()) {
            throw new EventNotFoundException("Event not found");
        }

        final UserApp userApp = userRepo.findById(userId).get();
        if (!eventIsFull(eventId) && !userAlreadyInEvent(userApp, eventId)) {
            eventRepo.getOne(eventId).incrementSignedUsers();
            return eventRepo.getOne(eventId).getUsers().add(userApp);
        } else if (eventIsFull(eventId)) {
            throw new EventIsFullException("Event is already full");
        } else if (userAlreadyInEvent(userApp, eventId)) {
            throw new UserAlreadyAssigned("User is already assigned to this event");
        } else {
            throw new RuntimeException("Upss, something went terrible wrong");
        }
    }

//    public Long addEventAsFavorite(HttpServletRequest request, Long eventId) {
//        final Long userId = util.getUserIdFromRequest(request);
//        final Optional<UserApp> optionalUserApp = userRepo.findById(userId);
//        final Optional<Event> optionalEvent = eventRepo.findById(eventId);
//
//        if (!optionalUserApp.isPresent()) {
//            throw new UserNotFoundException("User not found");
//        }
//        if (!optionalEvent.isPresent()) {
//            throw new EventNotFoundException("Event not found");
//        }
//
//        final Event event = optionalEvent.get();
//        final UserApp userApp = optionalUserApp.get();
//        if (userAlreadyFavouriteEvent(userApp, eventId)) {
//            throw new UserAlreadyAssigned("User already assigned event as favourite");
//        }
////        userApp.getFavouriteEvents().add(event);
//
//        return userRepo.save(userApp).getUserId();
//    }

    public FavouriteEvent addEventAsFavorite(HttpServletRequest request, Long eventId) {

        final Long userId = util.getUserIdFromRequest(request);
        final Optional<UserApp> optionalUserApp = userRepo.findById(userId);
        final Optional<Event> optionalEvent = eventRepo.findById(eventId);
        if (!optionalUserApp.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        if (!optionalEvent.isPresent()) {
            throw new EventNotFoundException("Event not found");
        }


        final Event event = optionalEvent.get();


        final FavouriteEvent favouriteEvent;
        final UserApp userApp = optionalUserApp.get();
        if (!userAlreadyFavouriteEvent(userApp, eventId)) {
            favouriteEvent = new FavouriteEvent();
            favouriteEvent.setFavourite(true);
        } else {
            favouriteEvent = favouriteEventRepo.findFavoriteEvent(eventId, userId);
            favouriteEvent.setFavourite(!favouriteEvent.getFavourite());
        }
        favouriteEvent.setEvent(event);
        favouriteEvent.setUserApp(userApp);


        return favouriteEventRepo.save(favouriteEvent);
//        userApp.getFavouriteEvents().add(event);

//        return userRepo.save(userApp).getUserId();
    }


    private boolean userAlreadyInEvent(UserApp userApp, Long eventId) {
        if (eventRepo.findUserEventByIds(eventId, userApp.getUserId()).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean userAlreadyFavouriteEvent(UserApp userApp, Long eventId) {
        return eventRepo.checkIfUserAlreadyFavouriteEvent(eventId, userApp.getUserId());


    }

    private boolean eventIsFull(Long id) {
        if (eventRepo.getOne(id).getGroupSize() > eventRepo.getOne(id).getSignedUsers()) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void removeUserFromEvent(HttpServletRequest request, Long eventId) {
        Long userId = util.getUserIdFromRequest(request);
        final Optional<Event> optionalEvent = eventRepo.findById(eventId);
        if (!optionalEvent.isPresent())
            throw new EventNotFoundException("Event not found with id " + eventId);
        final Event event = optionalEvent.get();
        event.decrementSignedUsers();
        eventRepo.save(event);
        eventRepo.delete(eventId, userId);
    }

    @Transactional
    public void removeEventFromFavourites(HttpServletRequest request, Long eventId) {
        Long userId = util.getUserIdFromRequest(request);
        final Optional<Event> optionalEvent = eventRepo.findById(eventId);
        if (!optionalEvent.isPresent())
            throw new EventNotFoundException("Event not found with id " + eventId);
        final Event event = optionalEvent.get();
        eventRepo.save(event);
        eventRepo.deleteFavouriteEvent(eventId, userId);
    }

    @Transactional
    public Event updateEvent(EventRequest eventRequest) throws MessagingException {
        final Optional<Event> eventOptional = eventRepo.findById(eventRequest.getEventId());
        if (!eventOptional.isPresent()) {
            throw new EventNotFoundException("Event not found");
        }
        final Event event = eventOptional.get();
        mapper.mapEventDtoToEvent(eventRequest, event);
        eventRepo.save(event);
        sendEmailAboutChangedDateOfEvent(eventRequest);
        sendEmailAboutChangedTimeOfEvent(eventRequest);
        return event;
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        if (!eventRepo.findById(eventId).isPresent())
            throw new EventNotFoundException("Event not found with id: " + eventId);
        eventRepo.deleteById(eventId);
    }

    private List<String> findEmailsByEventId(Long eventId) {
        return eventRepo.getEmailsOfSignedUsersToEvent(eventId);
    }

    private void sendEmailAboutChangedDateOfEvent(EventRequest eventRequest) throws MessagingException {
        final List<String> emailByEventId = findEmailsByEventId(eventRequest.getEventId());
        if (eventRequest.getDate() != null) {
            for (int i = 0; i < emailByEventId.size(); i++) {
                mailService.sendMail(emailByEventId.get(i), "Zmiana daty wydarzenia", "Informujemy o zmianie daty wydarzenia na: " + eventRequest.getDate(), false);
            }

        }

    }

    private void sendEmailAboutChangedTimeOfEvent(EventRequest eventRequest) throws MessagingException {
        final List<String> emailByEventId = findEmailsByEventId(eventRequest.getEventId());
        if (eventRequest.getTime() != null) {
            for (int i = 0; i < emailByEventId.size(); i++) {
                mailService.sendMail(emailByEventId.get(i), "Zmiana godziny wydarzenia", "Informujemy o zmianie daty wydarzenia na: " + eventRequest.getTime(), false);
            }

        }

    }

    public List<EventResponses> getFavouriteEvents(HttpServletRequest request) {
        final Long userId = util.getUserIdFromRequest(request);
        final List<EventResponses> eventResponses = eventRepo.getFavouriteEvents(userId);
        return eventResponses;
    }


}
