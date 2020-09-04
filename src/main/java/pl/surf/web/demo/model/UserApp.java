package pl.surf.web.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.surf.web.demo.facebook.model.AuthProvider;
import pl.surf.web.demo.validation.group.OnCreate;
import pl.surf.web.demo.validation.group.OnUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "Users")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserApp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = OnUpdate.class)
    @Column(name = "USER_ID")
    private Long userId;

    @NotBlank(message = "Username can't be empty", groups = OnCreate.class)
    @Null(groups = OnUpdate.class)
    private String username;

    private String name;

    private String phoneNumber;

    @NotBlank(message = "email can't be empty")
    @Null(groups = OnUpdate.class)
    private String email;

    @NotBlank(message = "Password can't be empty", groups = OnCreate.class)
    @Null(groups = OnUpdate.class)
    @Size(min = 8)
    private String password;


    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "users_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;

//    @ManyToMany(
//            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
//    )
//    @JoinTable(
//            name = "favourite_event",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "event_id")
//    )
//    private List<Event> favouriteEvents;
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Role role;

    private boolean enabled = true;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToMany(
            mappedBy = "users"
    )
    private Set<Video> videos = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="levelId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Level levelId;

    public UserApp() {
    }

//    public List<Event> getFavouriteEvents() {
//        return favouriteEvents;
//    }

//    public UserApp setFavouriteEvents(List<Event> favouriteEvents) {
//        this.favouriteEvents = favouriteEvents;
//        return this;
//    }

    public UserApp(@NotBlank(message = "Username can't be empty", groups = OnCreate.class) @Null(groups = OnUpdate.class) String username, String name,
                   @NotBlank(message = "email can't be empty") @Null(groups = OnUpdate.class) String email,
                   @NotBlank(message = "Password can't be empty", groups = OnCreate.class) @Null(groups = OnUpdate.class) @Size(min = 8) String password,
                   String imageUrl, @NotNull AuthProvider provider, Role role) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.role = role;
    }

    public UserApp(String username, String name, String email, String password, Role role, boolean enabled) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public UserApp(String username, String name, String phoneNumber, String email, String password, Role role) {
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserApp(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserApp(String username, String name, String email, String password, Role role){
        this(username,  name,  email,  password);
        this.role = role;
    }


    public List<Event> getEvents() {
        return events;
    }

    public UserApp setEvents(List<Event> events) {
        this.events = events;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getProviderId() {
        return providerId;
    }

    public Level getLevelId() {
        return levelId;
    }

    public void setLevelId(Level levelId) {
        this.levelId = levelId;
    }

    public Set<Video> getUserVideo() {
        return videos;
    }

    public void setUserVideo(Set<Video> videos) {
        this.videos = videos;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public UserApp setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserApp setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserApp setName(String name) {
        this.name = name;
        return this;
    }

    public UserApp setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    public UserApp setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserApp setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserApp setRole(Role role) {
        this.role = role;
        return this;
    }

    public UserApp setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public boolean getEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserApp userApp = (UserApp) o;
        return enabled == userApp.enabled &&
                Objects.equals(userId, userApp.userId) &&
                username.equals(userApp.username) &&
                name.equals(userApp.name) &&
                phoneNumber.equals(userApp.phoneNumber) &&
                email.equals(userApp.email) &&
                password.equals(userApp.password) &&
                role.equals(userApp.role);
    }


    @Override
    public int hashCode() {
        return Objects.hash(userId, username, name, phoneNumber, email, password, role, enabled);
    }

    @Override
    public String toString() {
        return "UserApp{" +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", enabled=" + enabled +
                '}';
    }
}
