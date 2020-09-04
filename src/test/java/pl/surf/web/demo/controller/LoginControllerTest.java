package pl.surf.web.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.surf.web.demo.facebook.model.AuthProvider;
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.RoleRepo;
import pl.surf.web.demo.repository.UserRepo;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class LoginControllerTest {
    public static final String PASSWORD = "Passfsrdfd";
    public static final String USERNAME = "USERNAME";
    public static final String NAME = "name";
    public static final String EMAIL_WP_PL = "email@wp.pl";
    public static final String ROLE_USER = "ROLE_USER";
    public static final AuthProvider AUTH_PROVIDER = AuthProvider.local;
    public static final String IMAGE_URL = "AuthProvider.loca";
    public static final Boolean EMAIL_VERIFIED = true;
//    public static final AuthProvider PROVIDER = AuthProvider.local;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;



    @Test
    void login() throws Exception {
        final Role role = roleRepo.findRoleByType(ROLE_USER);
        final String HASH_PW = BCrypt.hashpw(PASSWORD, BCrypt.gensalt());
        final UserApp savedUser = new UserApp( USERNAME, NAME,EMAIL_WP_PL, HASH_PW,  IMAGE_URL ,AUTH_PROVIDER ,role);
        userRepo.save(savedUser);
        //language=JSON5
        String user = "{\n" +
                "  \"username\": \"USERNAME\",\n" +
                "  \"password\": \"Passfsrdfd\"\n" +
                "}";

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwt").isString());

    }
}