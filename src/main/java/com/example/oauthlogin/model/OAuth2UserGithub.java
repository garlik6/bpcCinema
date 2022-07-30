package com.example.oauthlogin.model;

import com.example.oauthlogin.repositories.RoleRepository;
import com.example.oauthlogin.service.PictureSaverService;
import com.example.oauthlogin.service.SetupDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


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



    public String getLoginAttribute()
    {
        return (String) attributes.get("login");
    }

    public String getAvatarUriAttribute()
    {
        return (String) attributes.get("avatar_url");
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
