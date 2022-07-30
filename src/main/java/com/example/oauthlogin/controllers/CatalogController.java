package com.example.oauthlogin.controllers;

import com.example.oauthlogin.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {
    @Autowired
    private MovieRepository movieRepository;
    @GetMapping("/catalog")
    @Transactional
    String hello(Model model){
        model.addAttribute("movies",movieRepository.findAll());
        return "catalog";
    }
}
