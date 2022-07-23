package com.example.oauthlogin.controllers;

import com.example.oauthlogin.model.Movie;
import com.example.oauthlogin.model.User;
import com.example.oauthlogin.repositories.MovieRepository;
import com.example.oauthlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

@Controller
public class MovieController {


    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/movie")
    public String showMovie(Model model, @RequestParam String id, Authentication authentication) {
        Optional<Movie> movie = movieRepository.findById(id);
        movie.ifPresent(value -> model.addAttribute("movie", value));
        String username = getUsername(authentication);
        Optional<User> user = userRepository.findUserByUsername(username);
        if (movie.isPresent() && user.isPresent()){
            Boolean liked = user.get().isMovieLiked(movie.get());
            model.addAttribute("liked", liked);
        }

        return "movie";
    }

    public static String getUsername(Authentication authentication) {
        String username;


        if (authentication.getClass() == OAuth2AuthenticationToken.class) {
            username = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("login");

        } else {
            username = authentication.getName();
        }
        return username;
    }


    @PostMapping("/movie")
    public String like(@RequestParam String id, @RequestParam String option, Authentication authentication, Model model) {
        Optional<Movie> movie = movieRepository.findById(id);
        String username = getUsername(authentication);

        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty() || movie.isEmpty()) {
            return "movie";
        }
        Boolean liked = user.get().isMovieLiked(movie.get());
        if (Objects.equals(option, "like") && !liked) {
            user.get().addMovie(movie.get());
            liked = true;
            userRepository.save(user.get());
        }
        if (Objects.equals(option, "dislike") && liked) {
            user.get().removeMovie(movie.get());
            liked = false;
            userRepository.save(user.get());
        }
        movie.ifPresent(value -> model.addAttribute("movie", value));
        model.addAttribute("liked", liked);
        return "movie";
    }
}
