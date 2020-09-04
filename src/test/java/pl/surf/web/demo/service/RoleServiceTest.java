package pl.surf.web.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.repository.RoleRepo;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class RoleServiceTest {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    @Autowired
    RoleService roleService;
    @Autowired
    RoleRepo roleRepo;
//    @AfterEach
//    void tearDown() {
//        roleRepo.deleteAll();
//    }

    @Test
    void addRoleToDatabase() {
        final Role roleModerator = new Role(ROLE_MODERATOR);

        roleService.addRole(roleModerator);

        Role roleInDB = roleRepo.findRoleByType(ROLE_MODERATOR);

        assertThat(roleInDB.getType()).isEqualTo(ROLE_MODERATOR);
    }

    @Test
    void getRoleByName() {

        final Role result = roleService.getRoleByName(ROLE_USER);

        assertThat(result.getType()).isEqualTo(ROLE_USER);
    }
}