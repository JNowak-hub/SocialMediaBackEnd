package pl.surf.web.demo.facebook.security;


import pl.surf.web.demo.exceptions.OAuth2AuthenticationProcessingException;
import pl.surf.web.demo.facebook.model.AuthProvider;
import pl.surf.web.demo.facebook.model.FacebookOAuth2UserInfo;
import pl.surf.web.demo.facebook.model.OAuth2UserInfo;


import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
