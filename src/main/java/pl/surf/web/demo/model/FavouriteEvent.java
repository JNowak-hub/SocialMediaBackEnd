package pl.surf.web.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(EventFavaouritePK.class)
public class FavouriteEvent implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private UserApp userApp;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    private Event event;

    private Boolean isFavourite;

    public UserApp getUserApp() {
        return userApp;
    }

    public FavouriteEvent setUserApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public Event getEvent() {
        return event;
    }

    public FavouriteEvent setEvent(Event event) {
        this.event = event;
        return this;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public FavouriteEvent setFavourite(Boolean favourite) {
        isFavourite = favourite;
        return this;
    }
}
