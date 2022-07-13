package com.example.oauthlogin.service;

import com.example.oauthlogin.model.SecurityUser;
import com.example.oauthlogin.model.User;
import com.example.oauthlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class MyUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserRepository userRepository;

    public MyUserDetailsService() {
    }

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> loadedUser =  userRepository.findUserByUsername(username);

        if (loadedUser.isEmpty()){
            throw new BadCredentialsException("User Not Found");
        }

        return new SecurityUser(loadedUser.get());
    }
}
