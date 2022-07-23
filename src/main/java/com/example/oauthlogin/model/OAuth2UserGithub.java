package com.example.oauthlogin.model;

import com.example.oauthlogin.service.PictureSaverService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


public class OAuth2UserGithub implements OAuth2User {

    private final User user;

    private final String name;

    private final Map<String, Object> attributes;

    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }

    public OAuth2UserGithub(User user, String name, Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.name = name;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    public void populateUser() throws IOException {
        user.setUserType(UserType.GITHUB);
        user.setId(Integer.parseInt(name));
        String login = (String) attributes.get("login");
        String avatar_url = (String) attributes.get("avatar_url");
        user.setUsername(login);
        String profilePicUrl = PictureSaverService.savePicture(avatar_url,login);
        user.setProfilePicUrl(profilePicUrl);
    }

    @Override
    public String toString() {
        return "OAuth2UserGithub{" +
                "user=" + user +
                ", name='" + name + '\'' +
                ", attributes=" + attributes +
                ", authorities=" + authorities +
                '}';
    }
}
