package pl.surf.web.demo.controller;

import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.model.requests.UserRequest;
import pl.surf.web.demo.model.responses.UserResponses;
import pl.surf.web.demo.repository.UserRepo;

import pl.surf.web.demo.facebook.model.CurrentUser;
import pl.surf.web.demo.facebook.model.UserPrincipal;
import pl.surf.web.demo.service.UserService;
import pl.surf.web.demo.validation.group.OnCreate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
@Validated
@CrossOrigin("{cross.origin}")
public class UserController {

    private UserService userService;

    private UserRepo userRepo;

    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;

    }

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public Long registerUser(@Valid @RequestBody UserApp userApp) {
        return userService.registerUser(userApp);
    }

    //    @GetMapping("/user/me")
//    public UserApp getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
//        return userRepo.findById(userPrincipal.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
//    }
    @GetMapping("/user/me")
    public UserResponses getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getCurrentResponse(userPrincipal);
    }

    @PostMapping("/token_valid")
    public ResponseEntity<Boolean> validateToken(HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(userService.validateToken(request));
        } catch (SignatureException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(userService.validateToken(request));
        }
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserApp> updateUserAsUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        return ResponseEntity.ok(userService.updateUserByUser(userRequest, request));
    }

    @PatchMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserApp> updateUserAsAdmin(@RequestBody UserRequest userRequest, @RequestParam Long userId) {
        return ResponseEntity.ok(userService.updateUserByAdmin(userRequest, userId));
    }

}
