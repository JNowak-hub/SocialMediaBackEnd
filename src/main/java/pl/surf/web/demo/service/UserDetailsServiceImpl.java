package pl.surf.web.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.surf.web.demo.exceptions.UserNotFoundException;

import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.UserRepo;
import pl.surf.web.demo.facebook.model.UserPrincipal;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<UserApp> optionalUser = userRepo.findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found with username/email: " + usernameOrEmail);
        }
        final UserApp user = optionalUser.get();
        return UserPrincipal.create(user);
    }
}
