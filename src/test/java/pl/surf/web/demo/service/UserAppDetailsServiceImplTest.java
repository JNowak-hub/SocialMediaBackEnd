package pl.surf.web.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.RoleRepo;
import pl.surf.web.demo.repository.UserRepo;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserAppDetailsServiceImplTest {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    public static final String NAME = "name";
    public static final String EMAIL_WP_PL = "email@wp.pl";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "passsfgsaadfdrfd";
    public Role FOUND_ROLE;
    final String ROLE_USER = "ROLE_USER";


    @Test
    public void shouldLoadUserByUsernameTest() {

        FOUND_ROLE = roleRepo.findRoleByType(ROLE_USER);
        final UserApp userApp = new UserApp(USERNAME, NAME, EMAIL_WP_PL, PASSWORD, FOUND_ROLE);
        userRepo.save(userApp);


        final UserDetails result = userDetailsService.loadUserByUsername(USERNAME);
        final String role = Collections.singleton(result.getAuthorities()).toString();

        assertThat(result.getUsername()).isEqualTo(USERNAME);
        assertThat(result.getPassword()).isEqualTo(PASSWORD);
        assertThat(role.replace("[","").replace("]","")).isEqualTo(ROLE_USER);
    }

}
