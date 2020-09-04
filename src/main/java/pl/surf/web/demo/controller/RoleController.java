package pl.surf.web.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.service.RoleService;

@RestController
@RequestMapping("api/role")
@CrossOrigin(origins = "{cross.origin}")

public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long createRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }
}
