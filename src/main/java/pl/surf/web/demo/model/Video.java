package pl.surf.web.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Category category;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Level level;
    private String description;
    private String videoUrl;
    private String title;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "users_video",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @JsonIgnoreProperties("userVideo")
    private Set<UserApp> users = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Post post;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Set<UserApp> getUsers() {
        return users;
    }

    public void setUsers(Set<UserApp> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Video{" +
                ", categoryId=" + category +
                ", levelId=" + level +
                ", description='" + description + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(videoId, video.videoId) &&
                category.equals(video.category) &&
                level.equals(video.level) &&
                description.equals(video.description) &&
                videoUrl.equals(video.videoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, category, level, description, videoUrl);
    }
}