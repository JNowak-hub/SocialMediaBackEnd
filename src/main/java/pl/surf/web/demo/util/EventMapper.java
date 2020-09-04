package pl.surf.web.demo.util;

import org.springframework.stereotype.Component;
import pl.surf.web.demo.model.Event;
import pl.surf.web.demo.model.requests.EventRequest;
@Component
public class EventMapper {
    public void mapEventDtoToEvent(EventRequest eventRequest, Event event) {
        if (eventRequest.getDate() != (null))
            event.setDate(eventRequest.getDate());
        if (eventRequest.getDescription() != (null))
            event.setDescription(eventRequest.getDescription());

        if (eventRequest.getGroupSize() != (null))
            event.setGroupSize(eventRequest.getGroupSize());

        if (eventRequest.getLocation() != (null))
            event.setLocation(eventRequest.getLocation());

        if (eventRequest.getTitle() != (null))
            event.setTitle(eventRequest.getTitle());
        if (eventRequest.getTime() != (null))
            event.setTime(eventRequest.getTime());
    }

}
