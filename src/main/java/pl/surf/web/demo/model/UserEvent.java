package pl.surf.web.demo.model;

import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Component
@Table(name="UserEvents")
public class UserEvent {
    private Long userId;
    private Long eventId;

    public UserEvent() {
    }

    public UserEvent(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
