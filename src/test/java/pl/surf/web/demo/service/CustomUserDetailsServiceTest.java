//package pl.surf.web.demo.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import pl.surf.web.demo.model.Role;
//import pl.surf.web.demo.model.UserApp;
//import pl.surf.web.demo.repository.RoleRepo;
//import pl.surf.web.demo.repository.UserRepo;
//import CustomUserDetailsService;
//
//import java.util.Collections;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class CustomUserDetailsServiceTest {
//
//    @Autowired
//    UserRepo userRepo;
//    @Autowired
//    CustomUserDetailsService customUserDetailsServiceTest;
//
//    UserApp user1;
//    UserApp user2;
//    void setUp(){
//        user1 = new UserApp("username", "name", "mail@mail.pl", "password");
//        user2 = new UserApp("username2", "name2", "mail2@mail.pl", "password2");
//        userRepo.save(user1);
//        userRepo.save(user2);
//    }
//
//    @Test
//    @Transactional
//    public void loadUserByUsername(){
//        String username = "username2";
//
//        UserDetails user = customUserDetailsServiceTest.loadUserByUsername(username);
//
//        assertThat(user.getUsername()).isEqualTo(user1.getEmail());
//    }
//}
