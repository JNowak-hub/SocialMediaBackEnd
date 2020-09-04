package pl.surf.web.demo.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.surf.web.demo.facebook.service.CustomOAuth2UserService;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.UserRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomOAuth2UserServiceTest {
    @Autowired
    UserRepo userRepo;

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    void setUp(){
        UserApp user1 = new UserApp("username", "name", "email@gmail.com", "password");
        UserApp user2 = new UserApp("username2", "name2", "email2@gmail.com", "password2");

        userRepo.save(user1);
        userRepo.save(user2);
    }

}
