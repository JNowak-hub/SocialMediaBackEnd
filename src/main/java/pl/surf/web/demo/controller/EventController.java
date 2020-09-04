package pl.surf.web.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.surf.web.demo.model.Event;
import pl.surf.web.demo.model.requests.EventRequest;
import pl.surf.web.demo.model.responses.EventResponses;
import pl.surf.web.demo.service.EventService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "{cross.origin}")
public class EventController {
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Event addEvent(@RequestBody Event event) {
        return eventService.addEvent(event);
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getCurrentEvents2();
    }

    @PostMapping("tp")
    @CrossOrigin(allowedHeaders = {"Authorization"}, exposedHeaders = {"Authorization"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public void takePartInEvent(HttpServletRequest request, @RequestParam Long eventId) {
        eventService.takePartInEvent(request, eventId);
    }

    @PostMapping("/favourite")
    @CrossOrigin(allowedHeaders = {"Authorization"}, exposedHeaders = {"Authorization"})
    @ResponseStatus(code = HttpStatus.OK)
    public void addEventAsFavourite(HttpServletRequest request, @RequestParam Long eventId) {
        eventService.addEventAsFavorite(request, eventId);
    }

    @PostMapping("/delete/user")
    @CrossOrigin(allowedHeaders = {"Authorization"}, exposedHeaders = {"Authorization"})
    @ResponseStatus(code = HttpStatus.OK)
    public void removeUserFromEvent(HttpServletRequest request, Long eventId) {
        eventService.removeUserFromEvent(request, eventId);
    }

    @DeleteMapping("/favourite")
    @CrossOrigin(allowedHeaders = {"Authorization"}, exposedHeaders = {"Authorization"})
    @ResponseStatus(code = HttpStatus.OK)
    public void removeEventFromFavourites(HttpServletRequest request, Long eventId) {
        eventService.removeEventFromFavourites(request, eventId);
    }

    @GetMapping("/favourite")
    @ResponseStatus(code = HttpStatus.OK)
    public List<EventResponses> getEvent(HttpServletRequest request) {
        return eventService.getFavouriteEvents(request);
    }


    @PatchMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Event updateEvent(@RequestBody @Valid EventRequest eventRequest) throws MessagingException {
        return eventService.updateEvent(eventRequest);


    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }

}
