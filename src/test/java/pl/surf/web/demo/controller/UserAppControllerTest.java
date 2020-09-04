package pl.surf.web.demo.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.RoleRepo;
import pl.surf.web.demo.repository.UserRepo;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class UserAppControllerTest {
    public static final String NAME = "name";
    public static final String EMAIL_WP_PL = "email@wp.pl";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "passdhtffhddfd";
    public static final String ROLE_USER_STRING = "ROLE_USER";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;


    static Role ROLE_USER;


    @BeforeEach
    void init() {
        ROLE_USER = roleRepo.findRoleByType(ROLE_USER_STRING);
    }

//    @AfterEach
//    void tearDown() {
//        userRepo.deleteAll();
//    }

    @Test
    void addUser() throws Exception {
        //language=JSON
        String user = "{\"username\": \"djbak\",\n" +
                "  \"password\": \"ajkDSAsgsrttr\",\n" +
                "    \"name\": \"name\",\n" +
                "    \"surname\":\"surname\",\n" +
                "    \"email\": \"email\", \n" +
                "    \"provider\": \"local\"\n" +
                "}";

        mockMvc.perform(post("/api/users/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))

                .andExpect(status().isCreated());
        final UserApp result = userRepo.findUserByUsername("djbak").get();
        BCrypt.checkpw(PASSWORD, result.getPassword());
        assertEquals("name", result.getName());
        assertEquals("email", result.getEmail());
        assertEquals("djbak", result.getUsername());
    }

}