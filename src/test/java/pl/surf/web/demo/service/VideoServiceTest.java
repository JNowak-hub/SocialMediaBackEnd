package pl.surf.web.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.surf.web.demo.model.Video;
import pl.surf.web.demo.repository.VideoRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class VideoServiceTest {
    public static final String SOME_DECRIPTION = "some decription";
    public static final String URL_TO_VIDEO = "https://something.pl";
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoRepo videoRepo;

    @AfterEach
    void tearDown() {
        videoRepo.deleteAll();
    }

    @Test
    void shouldAddVideo() {
        final Video video = new Video();
        video.setDescription(SOME_DECRIPTION);
        video.setVideoUrl(URL_TO_VIDEO);

        videoService.createVideo(video);
        final Video videoFromDB = videoRepo.findById(3L).get();

        assertThat(videoFromDB.getVideoId()).isEqualTo(3L);
        assertThat(videoFromDB.getDescription()).isEqualTo(SOME_DECRIPTION);
        assertThat(videoFromDB.getVideoUrl()).isEqualTo(URL_TO_VIDEO);
    }

    @Test
    void getVideos() {
        final Video video = new Video();
        video.setDescription(SOME_DECRIPTION);
        video.setVideoUrl(URL_TO_VIDEO);
        videoRepo.save(video);

        final List<Video> videos = videoService.getVideos();

        assertEquals(2, videos.size());
        assertThat(videos.get(1).getDescription()).isEqualTo(SOME_DECRIPTION);
        assertThat(videos.get(1).getVideoUrl()).isEqualTo(URL_TO_VIDEO);
    }
}