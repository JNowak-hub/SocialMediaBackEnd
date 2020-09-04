package pl.surf.web.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.repository.RoleRepo;

@Service
public class RoleService {

    private RoleRepo roleRepo;


    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Transactional
    public Long addRole(Role role) {
        final Role cratedRole = roleRepo.save(role);
        return cratedRole.getRoleId();
    }

    public Role getRoleByName(String name) {
        return roleRepo.findRoleByType(name);
    }
    public Role getRoleById(Long id) {
        return roleRepo.getOne(id);
    }
}
