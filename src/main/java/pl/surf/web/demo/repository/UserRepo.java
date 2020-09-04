package pl.surf.web.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.surf.web.demo.model.UserApp;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findUserByUsername(String name);
    Optional<UserApp> findUserByEmail(String email);
    Optional<UserApp> findUserByUsernameOrEmail(String name,String email);
    Boolean existsByEmail(String email);

}
