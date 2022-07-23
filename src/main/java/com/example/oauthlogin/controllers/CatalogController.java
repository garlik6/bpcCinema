package com.example.oauthlogin.controllers;

import com.example.oauthlogin.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {
    @Autowired
    private MovieRepository movieRepository;
    @GetMapping("/catalog")
    String hello(Model model){
        model.addAttribute("movies",movieRepository.findAll());
        return "catalog";
    }
}
