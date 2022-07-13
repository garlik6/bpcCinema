package com.example.oauthlogin.config;

import com.example.oauthlogin.model.OAuth2UserGithub;
import com.example.oauthlogin.model.User;
import com.example.oauthlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.IOException;

import static org.springframework.security.config.oauth2.client.CommonOAuth2Provider.GITHUB;

@Configuration
public class OAuth2Config {

    @Autowired
    UserRepository userRepository;

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request -> {
            OAuth2User oAuth2User = delegate.loadUser(request);
//            System.out.println(":)");//do database logic
            User user = new User();
            OAuth2UserGithub oAuth2UserGithub = new OAuth2UserGithub(user,
                    oAuth2User.getName(),
                    oAuth2User.getAttributes(),
                    oAuth2User.getAuthorities());
            try {
                oAuth2UserGithub.populateUser();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            userRepository.save(user);
            return oAuth2UserGithub;
        };
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }

    private ClientRegistration clientRegistration() {
        return GITHUB.getBuilder("github")
                .clientSecret("4e029161d9935e731a3cc1cdad69d4690f3a8cec")
                .clientId("93f4591631da742d6aa6").build();
    }
}
