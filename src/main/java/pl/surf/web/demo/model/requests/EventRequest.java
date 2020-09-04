package pl.surf.web.demo.model.requests;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventRequest {

    @NotNull(message = "eventId can't be empty")
    private Long eventId;

    private String title;

    private String description;

    private String location;

    @FutureOrPresent(message = "Date must be future or present")
    private LocalDate date;

    private LocalTime time;

    private Integer signedUsers = 0;

    private Integer groupSize;

    public Long getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public EventRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalTime getTime() {
        return time;
    }

    public EventRequest setTime(LocalTime time) {
        this.time = time;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public EventRequest setLocation(String location) {
        this.location = location;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public EventRequest setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getSignedUsers() {
        return signedUsers;
    }

    public EventRequest setSignedUsers(Integer signedUsers) {
        this.signedUsers = signedUsers;
        return this;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public EventRequest setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
        return this;
    }
}
