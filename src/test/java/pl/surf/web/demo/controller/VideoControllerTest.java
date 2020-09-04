package pl.surf.web.demo.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.surf.web.demo.model.*;
import pl.surf.web.demo.repository.CategoryRepo;
import pl.surf.web.demo.repository.LevelRepo;
import pl.surf.web.demo.repository.VideoRepo;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class VideoControllerTest {
    public static final String SOME_DESCRIPTION = "some description";
    public static final String VIDEO_URL = "https....";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    VideoRepo videoRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    LevelRepo levelRepo;

    @AfterEach
    void tearDown() {
        videoRepo.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addVideo() throws Exception {
        //language=JSON5
        String video = "{\n" +
                "  \"description\": \"some description\",\n" +
                "  \"videoUrl\": \"https....\"\n" +
                "}";
        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(video))

                .andExpect(status().isCreated());
        final Video videoInDB = videoRepo.findById(2L).get();
        assertEquals(VIDEO_URL, videoInDB.getVideoUrl());
        assertEquals(SOME_DESCRIPTION, videoInDB.getDescription());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getVideos() throws Exception {
        final Video video = new Video();
        video.setDescription(SOME_DESCRIPTION);
        video.setVideoUrl(VIDEO_URL);

        videoRepo.save(video);
        mockMvc.perform(get("/api/videos")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description", is(SOME_DESCRIPTION)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].videoUrl", is(VIDEO_URL)));
        ;
    }
}