package pl.surf.web.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Events")
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private String location;

    @NotNull(message = "Date can't be empty")
    @FutureOrPresent(message = "Date must be future or present")
    private LocalDate date;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "HH:mm", locale = "en_GB")
    @NotNull(message = "Time can't be empty")
    private LocalTime time;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Level level;

    private Integer signedUsers = 0;

    @NotNull(message = "Group size can't be empty")
    private Integer groupSize;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(
            name = "users_event",
            inverseJoinColumns = {@JoinColumn(name = "user_id")},
            joinColumns = {@JoinColumn(name = "event_id")}
    )
    @JsonIgnoreProperties("events")
    private List<UserApp> users = new ArrayList<>();

    public Event() {
    }

    public Event(String title, String description, String location, LocalDate date, Integer groupSize) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.groupSize = groupSize;
    }

    public Event(@NotNull String title, String description, @NotNull String location, @NotNull(message = "Date can't be empty") @FutureOrPresent(message = "Date must be future or present") LocalDate date, @NotNull(message = "Time can't be empty") @FutureOrPresent(message = "Time must be future or present") LocalTime time, Level level, @NotNull(message = "Group size can't be empty") Integer groupSize) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.time = time;
        this.level = level;
        this.groupSize = groupSize;
    }


    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public List<UserApp> getUsers() {
        return users;
    }

    public void setUsers(List<UserApp> users) {
        this.users = users;
    }

    public Integer getSignedUsers() {
        return signedUsers;
    }

    public void setSignedUsers(Integer signedUsers) {
        this.signedUsers = signedUsers;
    }

    public void incrementSignedUsers() {
        this.signedUsers++;
    }

    public void decrementSignedUsers() {
        this.signedUsers--;
    }
}
