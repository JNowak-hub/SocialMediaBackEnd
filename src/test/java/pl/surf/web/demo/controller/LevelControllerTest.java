package pl.surf.web.demo.controller;

import org.junit.Test;
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
import pl.surf.web.demo.repository.LevelRepo;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LevelControllerTest {

    public static final String LEVEL_DESCRIPTION = "HARD";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LevelRepo levelRepo;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addLevel_added() throws Exception {
        //language=JSON
        String level = "{\n" +
                "  \"levelDescription\":\"HARD\"\n" +
                "}";

        mockMvc.perform(post("/api/level")
                .contentType(MediaType.APPLICATION_JSON)
                .content(level))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(2)));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getLevel_byId() throws Exception {

        mockMvc.perform(get("/api/level/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.levelDescription", is("EASY")));

    }
}
