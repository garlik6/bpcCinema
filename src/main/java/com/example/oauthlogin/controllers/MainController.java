package com.example.oauthlogin.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;

@Controller
@ControllerAdvice
public class MainController {
    @Value("${profilePicture.path}")
    private String profilePicturePath;
    @Value("${profilePicture.format}")
    private String ProfilePictureFormat;

    @GetMapping
    public String hello(Authentication authentication, Model model) throws IOException {
        if (authentication.getClass() == OAuth2AuthenticationToken.class) {
            String username = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("login");
            String profilePicURL = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("avatar_url");
            String HtmlPathToPic = "/images/" + username + "_pic." + ProfilePictureFormat;
            model.addAttribute("username", username);
            model.addAttribute("profilePicture", HtmlPathToPic);
            model.addAttribute("auth", ((OAuth2AuthenticationToken) authentication).getPrincipal());
        }
        return "main";
    }


    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("msg", "Welcome to the Netherlands!");
    }
}
