package pl.surf.web.demo.configuration;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.repository.UserRepo;
import pl.surf.web.demo.facebook.model.UserPrincipal;


import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {


    @Autowired
    private AppProperties appProperties;

    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);


    private String extractRole(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities()
                .toString()
                .replace('[', ' ')
                .replace(']', ' ')
                .trim();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractUserId(String token) {
        return Long.valueOf(extractClaim(token, Claims::getSubject));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        return createToken((UserPrincipal) userDetails);
    }

    public String generateToken(Authentication authentication) {
        return createToken(authentication);
    }

    private String createToken(UserPrincipal userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .claim("email", userDetails.getEmail())
                .claim("username", userDetails.getUsername())
                .claim("role", extractRole(userDetails))
                .setId(String.valueOf(userDetails.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret()).compact();
    }

    private String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .claim("email", userPrincipal.getUsername())
                .claim("role", extractRole(userPrincipal))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    private String extractRole(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .toString()
                .replace('[', ' ')
                .replace(']', ' ')
                .trim();
    }


    public Boolean validateToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        if (header == null) {
            return false;
        }
        String clearToken = null;

        if (header != null && header.startsWith("Bearer ")) {
            clearToken = header.substring(7);
        }
        final Long userId = extractUserId(clearToken);
        final UserApp userDetails = userRepo.findById(userId).get();

        final Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(clearToken).getBody();
        final Object role = claims.get("role");
        final String email = (String) claims.get("email");

        if (email.equals(userDetails.getEmail()) && role.equals(userDetails.getRole().getType())) {
            return true;

        }
        return false;
    }
    public Long getUserIdFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String clearToken;

        Long userId = null;
        if ((header != null) && header.startsWith("Bearer ")) {
            clearToken = header.substring(7);
            userId = this.extractUserId(clearToken);
        }
        return userId;
    }

}
