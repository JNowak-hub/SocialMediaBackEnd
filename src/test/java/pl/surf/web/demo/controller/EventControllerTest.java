package pl.surf.web.demo.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.surf.web.demo.model.Event;
import pl.surf.web.demo.model.Level;
import pl.surf.web.demo.repository.EventRepo;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class EventControllerTest {
    public static final String TITLE = "some title";
    public static final String LOCATION = "Kato";
    public static final int GROUP_SIZE = 1;
    public static final String DESCRIPTION = "description";
    public static final String DATE = "2100-07-21";
    public static final String TIME = "15:00";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepo eventRepo;

    @AfterEach
    void tearDown() {
        eventRepo.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addEvent() throws Exception {
        //language=JSON5
        String event = "{\n" +
                "  \"title\":\"some title\",\n" +
                "  \"date\":\"2100-07-21\",\n" +
                "  \"time\":\"21:34\",\n" +
                "  \"level\":{\n" +
                "    \"id\":1\n" +
                "  },\n" +
                "  \"description\":\"des\",\n" +
                "  \"location\":\"Kato\",\n" +
                "  \"groupSize\":1\n" +
                "}";

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(event))

                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser("USER")
    void getCurrentEvents() throws Exception {

        final Event event = new Event(TITLE, DESCRIPTION, LOCATION, LocalDate.parse(DATE), LocalTime.parse(TIME), null, GROUP_SIZE);
        eventRepo.save(event);
        mockMvc.perform(get("/api/events")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title", is(TITLE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].date", is(DATE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description", is(DESCRIPTION)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].location", is(LOCATION)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].groupSize", is(GROUP_SIZE)));
    }

    @Test
    void takePartInEvent() {
    }
}