package pl.surf.web.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.surf.web.demo.exceptions.UserNotFoundException;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    private UserRepo userRepo;
    @Autowired
    @Lazy
    private BCryptPasswordEncoder encoder;


    public CustomAuthenticationManager(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String nameOrEmail = authentication.getName();
        String password = authentication.getCredentials().toString();

        final Optional<UserApp> userOptional = userRepo.findUserByUsernameOrEmail(nameOrEmail,nameOrEmail);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with username/email " + nameOrEmail + "not found");

        }
        if (!encoder.matches(password, userOptional.get().getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userOptional.get().getRole().toString()));
        Authentication auth = new
                UsernamePasswordAuthenticationToken(nameOrEmail, password, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;

    }
}