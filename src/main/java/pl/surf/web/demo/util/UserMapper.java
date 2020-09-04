package pl.surf.web.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.model.requests.UserRequest;
import pl.surf.web.demo.model.responses.UserResponses;
import pl.surf.web.demo.service.LevelService;
import pl.surf.web.demo.service.RoleService;

@Component
public class UserMapper {

    private LevelService levelService;
    private BCryptPasswordEncoder encoder;
    private RoleService roleService;

    public UserMapper(BCryptPasswordEncoder encoder, LevelService levelService, RoleService roleService) {
        this.encoder = encoder;
        this.levelService = levelService;
        this.roleService = roleService;
    }


    public void mapUserRequestToUserByUser(UserRequest userRequest, UserApp userApp) {
        if (userRequest.getUsername() != (null))
            userApp.setUsername(userRequest.getUsername());
        if (userRequest.getName() != (null))
            userApp.setName(userRequest.getName());
        if (userRequest.getPhoneNumber() != (null))
            userApp.setPhoneNumber(userRequest.getPhoneNumber());
        if (userRequest.getPassword() != (null))
            userApp.setPassword(encoder.encode(userRequest.getPassword()));
        if (userRequest.getImageUrl() != (null))
            userApp.setImageUrl(userRequest.getImageUrl());
    }

    public void mapUserRequestToUserByAdmin(UserRequest userRequest, UserApp userApp) {
        if (userRequest.getRoleId() != (null)) {
            userApp.setRole(roleService.getRoleById(userRequest.getRoleId()));
        }
        if (userRequest.getLevelId() != (null)) {
            userApp.setLevelId(levelService.getLevelById(userRequest.getLevelId()));
        }
    }


    public void mapUserAppToResponse(UserApp userApp, UserResponses userResponses) {
        if (userApp.getName() != null)
            userResponses.setName(userApp.getName());

        if (userApp.getUsername() != null)
            userResponses.setUsername(userApp.getUsername());

        if (userApp.getEmail() != null) {
            userResponses.setEmail(userApp.getEmail());
        }

    }
}
