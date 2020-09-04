package pl.surf.web.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.surf.web.demo.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findRoleByType(String name);
}
