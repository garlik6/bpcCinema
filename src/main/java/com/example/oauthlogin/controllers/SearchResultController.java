package com.example.oauthlogin.controllers;

import com.example.oauthlogin.model.Movie;
import com.example.oauthlogin.service.MovieDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchResultController {

    @Autowired
    private MovieDataService movieDataService;


    @PostMapping("/searchResult")
    String processSearchRequest(@RequestParam String query, Model model) {
        Movie movie = movieDataService.getMovieByName(query);
        if (movie.getTitle() == null) {
            model.addAttribute("notFound", "Not found");
        } else {
            movieDataService.saveMovie(movie);
            model.addAttribute("notFound", "");
            model.addAttribute("query", query);
            model.addAttribute("movie", movie);
        }
        return "searchResult";
    }
}
