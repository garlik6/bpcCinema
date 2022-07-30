package com.example.oauthlogin.controllers;

import com.example.oauthlogin.exeptions.movieNotFoundException;
import com.example.oauthlogin.model.Movie;
import com.example.oauthlogin.model.User;
import com.example.oauthlogin.repositories.MovieRepository;
import com.example.oauthlogin.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ApiController {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @GetMapping("/api/dashboard")
    public String helloAdmin(Model model) {
        return "Dashboard";
    }

    @GetMapping("/api/dashboard/movies")
    public String movies(Model model) {
        model.addAttribute("movies",
                movieRepository.findAll()
                        .stream()
                        .sorted(Comparator.comparing(Movie::getTitle))
                        .collect(Collectors.toList()));
        return "DashboardMovies";
    }

    @GetMapping("/api/dashboard/users")
    public String users(Model model) {
        model.addAttribute("users",
                userRepository.findAll().
                        stream().filter(
                                u -> u.getRoles()
                                        .stream()
                                        .noneMatch(role -> Objects.equals(role.getName(), "ROLE_ADMIN"))
                        )
                        .sorted(Comparator.comparing(User::getUsername))
                        .collect(Collectors.toList()));
        return "DashboardUsers";
    }

    @DeleteMapping("/api/dashboard/movies")
    @Transactional
    public String deleteMovie(@RequestParam String id, Model model) throws movieNotFoundException {

        Optional<Movie> movieToDelete = movieRepository.findById(id);
        if (movieToDelete.isPresent()) {
            movieRepository.delete(movieToDelete.get());
        } else throw new movieNotFoundException();
        model.addAttribute(movieRepository.findAll().stream().sorted(Comparator.comparing(Movie::getTitle)));
        return "DashboardMovies";
    }

    @DeleteMapping("/api/dashboard/users")
    @Transactional
    public String deleteUser(@RequestParam int id, Model model) throws movieNotFoundException {

        Optional<User> userToDelete = userRepository.findUserById(id);
        if (userToDelete.isPresent()) {
            userRepository.delete(userToDelete.get());
        } else throw new movieNotFoundException();
        model.addAttribute(movieRepository.findAll().stream().sorted(Comparator.comparing(Movie::getTitle)));
        return "DashboardMovies";
    }
}
