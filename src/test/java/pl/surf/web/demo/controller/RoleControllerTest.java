package pl.surf.web.demo.controller;

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
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.repository.RoleRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class RoleControllerTest {
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    RoleRepo roleRepo;

    @Test
    @WithMockUser(roles = "USER")
    void createRole() throws Exception {
        //language=JSON
        String role = "{\n" +
                "  \"type\":\"ROLE_MODERATOR\"\n" +
                "}";

        mockMvc.perform(post("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(role))

                .andExpect(status().isCreated());

        final Role result = roleRepo.findRoleByType(ROLE_MODERATOR);

        assertEquals(ROLE_MODERATOR, result.getType());
    }
}