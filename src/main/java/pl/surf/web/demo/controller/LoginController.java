package pl.surf.web.demo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.surf.web.demo.model.requests.AuthenticationRequest;
import pl.surf.web.demo.model.responses.AuthenticationResponse;
import pl.surf.web.demo.service.LoginService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("api/login")
public class LoginController {
    @Value("${urlfailredirect.allUri}")
    private String redirectFailUri;
    @Value("${urlCorrectlyRedirect.allUri}")
    private String correctlyRedirect;

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return loginService.login(authenticationRequest);
    }

    @GetMapping("{error}")
    public ResponseEntity<String> failFacebookAuthentication(@Valid @PathVariable String error, HttpServletResponse response) {
        try {
//            response.sendRedirect("http://localhost:5000/");
            response.sendRedirect(redirectFailUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.PERMANENT_REDIRECT);
    }

    @GetMapping()
    public ResponseEntity<String> authenticateFacebookUser(@Valid @RequestParam String token, HttpServletResponse response) {
        response.setHeader("Authorization", "Bearer " + token);

        try {
            response.sendRedirect(correctlyRedirect + token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
