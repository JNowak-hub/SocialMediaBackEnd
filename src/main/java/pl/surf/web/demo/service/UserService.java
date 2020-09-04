package pl.surf.web.demo.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pl.surf.web.demo.configuration.JwtUtil;
import pl.surf.web.demo.exceptions.EmailIsAlreadyInUseException;
import pl.surf.web.demo.exceptions.ResourceNotFoundException;
import pl.surf.web.demo.exceptions.UserNotFoundException;
import pl.surf.web.demo.exceptions.UserWithUsernameAlreadyExistException;
import pl.surf.web.demo.facebook.model.UserPrincipal;
import pl.surf.web.demo.model.Role;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.model.requests.UserRequest;
import pl.surf.web.demo.model.responses.UserResponses;
import pl.surf.web.demo.repository.EventRepo;
import pl.surf.web.demo.repository.UserRepo;
import pl.surf.web.demo.util.UserMapper;
import pl.surf.web.demo.validation.group.OnUpdate;


import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserService {


    private RoleService roleService;
    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EventRepo eventRepo;
    private JwtUtil util;
    private UserMapper userMapper;

    public UserService(RoleService roleService, UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder, EventRepo eventRepo, JwtUtil util, UserMapper userMapper) {
        this.roleService = roleService;
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.eventRepo = eventRepo;
        this.util = util;
        this.userMapper = userMapper;
    }

    @Transactional
    public Long registerUser(final UserApp userApp) {

        if (userRepo.findUserByUsername(userApp.getUsername()).isPresent()) {
            throw new UserWithUsernameAlreadyExistException("Username is already in use");
        }
        if (userRepo.findUserByEmail(userApp.getEmail()).isPresent()) {
            throw new EmailIsAlreadyInUseException("Email is already in use");
        }


        final Role roleUser = roleService.getRoleByName("ROLE_USER");
        userApp.setEnabled(true);
        userApp.setPassword(bCryptPasswordEncoder.encode(userApp.getPassword()));
        userApp.setRole(roleUser);
        final UserApp createdUserApp = userRepo.save(userApp);
        return createdUserApp.getUserId();

    }


    public boolean validateToken(HttpServletRequest request) {
        return util.validateToken(request);
    }

    public UserResponses getCurrentResponse(UserPrincipal userPrincipal) {
        final UserResponses userResponses = new UserResponses();
        final UserApp userApp = userRepo.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        userMapper.mapUserAppToResponse(userApp, userResponses);
        return userResponses;
    }

    public UserApp updateUserByUser(UserRequest userRequest, HttpServletRequest request) {
        Long userId = util.getUserIdFromRequest(request);

        Optional<UserApp> userOptional = userRepo.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("user not found with id : " + userId);
        }
        UserApp user = userOptional.get();

        userMapper.mapUserRequestToUserByUser(userRequest, user);

        userRepo.save(user);

        return user;
    }

    @Validated(OnUpdate.class)
    public UserApp updateUserByAdmin(UserRequest userRequest, Long userId) {

        Optional<UserApp> userOptional = userRepo.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("user not found with id : " + userId);
        }
        UserApp user = userOptional.get();

        userMapper.mapUserRequestToUserByAdmin(userRequest, user);

        userRepo.save(user);

        return user;
    }

}
