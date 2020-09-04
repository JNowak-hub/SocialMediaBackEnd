package pl.surf.web.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.surf.web.demo.model.Video;


public interface VideoRepo extends JpaRepository<Video,Long> {
    @Modifying
    @Query(value = "UPDATE VIDEOS SET category_id = ?1 WHERE video_id = ?2"
            , nativeQuery = true)
    Integer changeCategoryById(Long categoryId, Long videoId);

    @Modifying
    @Query(value = "UPDATE VIDEOS SET description = ?1 WHERE video_id = ?2"
            , nativeQuery = true)
    Integer changeDescription(String newDescription, Long videoId);

    @Modifying
    @Query(value = "UPDATE VIDEOS SET level_id = ?1 WHERE video_id = ?2"
            , nativeQuery = true)
    Integer changeLevelById(Long levelId, Long videoId);

    @Modifying
    @Query(value = "UPDATE VIDEOS SET video_url = ?1 WHERE video_id = ?2"
            , nativeQuery = true)
    Integer changeUrl(String newUrl, Long videoId);

    @Query(value = "SELECT EXISTS(SELECT * FROM videos WHERE video_id=?1)"
            , nativeQuery = true)
    Boolean checkIfVideoExists(Long videoId);

}
