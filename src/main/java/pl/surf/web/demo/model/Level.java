package pl.surf.web.demo.model;

import pl.surf.web.demo.validation.group.OnCreate;
import pl.surf.web.demo.validation.group.OnUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Entity
@Table(name = "Levels")
public class Level implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = OnCreate.class, message = "level description can't be empty")
    @Null(groups = OnUpdate.class)
    private String levelDescription;

    public Level() {
    }

    public Level(@NotBlank String levelDescription) {
        this.levelDescription = levelDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelDescription() {
        return levelDescription;
    }

    public void setLevelDescription(String levelDescription) {
        this.levelDescription = levelDescription;
    }
}
