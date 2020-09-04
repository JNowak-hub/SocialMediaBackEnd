package pl.surf.web.demo.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.surf.web.demo.model.requests.AuthenticationRequest;
import pl.surf.web.demo.model.responses.AuthenticationResponse;
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.RoleRepo;
import pl.surf.web.demo.repository.UserRepo;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LoginServiceTest {


    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(LoginServiceTest.class);
    private static final String NAME = "name";
    private static final String EMAIL_WP_PL = "email@wp.pl";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "pass";
    public static final String ROLE_USER = "ROLE_USER";

    public Role FOUND_ROLE;

    @Test
    public void shouldLoginAndReturnJWT() throws Exception {
        FOUND_ROLE = roleRepo.findRoleByType(ROLE_USER);
        final String HASHED_PW = BCrypt.hashpw(PASSWORD, BCrypt.gensalt());

        final UserApp userApp = new UserApp(USERNAME, NAME, EMAIL_WP_PL, HASHED_PW, FOUND_ROLE, true);
        userRepo.save(userApp);
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest(USERNAME, PASSWORD);

        final ResponseEntity<AuthenticationResponse> login = loginService.login(authenticationRequest);
        final String jwt = login.getBody().getJwt();

        log.info(jwt);

    }
}
