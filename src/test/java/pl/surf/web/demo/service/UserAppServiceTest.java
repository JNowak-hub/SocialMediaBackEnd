package pl.surf.web.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.surf.web.demo.facebook.model.AuthProvider;
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.RoleRepo;

import pl.surf.web.demo.repository.UserRepo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class UserAppServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    public static final String NAME = "name";
    public static final String EMAIL_WP_PL = "email@wp.pl";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "passsafsfwfs";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String NEW_USERNAME = "newUsername";
    public static final String MY_NEW_EMIAL_GMAIL_COM = "myNewEmial@gmail.com";
    public static final AuthProvider AUTH_PROVIDER = AuthProvider.local;
    public static final String IMAGE_URL = "AuthProvider.loca";
    public static final Boolean EMAIL_VERIFIED = true;
    public static final String PROVIDER_ID = "1";

    public static final Role ROLE = new Role("ROLE_USER");


//    @AfterEach
//    void tearDown() {
//        userRepo.deleteAll();
//    }



    @Test
    void addUserWithDetailsAsUser() {
        final Role role = roleRepo.findRoleByType(ROLE_USER);
        final String HASH_PW = BCrypt.hashpw(PASSWORD, BCrypt.gensalt());
        final UserApp userApp = new UserApp( USERNAME, NAME,EMAIL_WP_PL, HASH_PW,  IMAGE_URL ,AUTH_PROVIDER , role);


        userService.registerUser(userApp);
        UserApp result = userRepo.findById(2L).get();

        BCrypt.checkpw(PASSWORD, result.getPassword());
        assertThat(result.getName()).isEqualTo(NAME);
        assertThat(result.getEmail()).isEqualTo(EMAIL_WP_PL);
        assertThat(result.getUsername()).isEqualTo(USERNAME);

    }


}