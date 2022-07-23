package com.example.oauthlogin.controllers;

import com.example.oauthlogin.model.User;
import com.example.oauthlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class CollectionController {


    public final UserRepository userRepository;

    @Autowired
    public CollectionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/collection")
    String collection(Authentication authentication, Model model) {
            String username = MovieController.getUsername(authentication);
            Optional<User> user = userRepository.findUserByUsername(username);
            user.ifPresent(value -> model.addAttribute("movies",value.getMovies()));
            return "catalog";
    }
}
