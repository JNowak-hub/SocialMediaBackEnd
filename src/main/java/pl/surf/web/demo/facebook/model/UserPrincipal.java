package pl.surf.web.demo.facebook.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pl.surf.web.demo.model.UserApp;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {
    private Long id;
    private String email;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.username = username;
    }


    public static UserPrincipal create(UserApp user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(user.getRole().getType()));
        if (user.getProvider().name().equals("local")) {
            return new UserPrincipal(
                    user.getUserId(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities,
                    user.getUsername()
            );
        } else {
            return new UserPrincipal(
                    user.getUserId(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        }

    }

    public String getEmail() {
        return email;
    }

    public static UserPrincipal create(UserApp user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return id;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        if (username != null) {
            return username;
        } else {
            return email;
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}

