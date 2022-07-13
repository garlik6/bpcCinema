package com.example.oauthlogin.controllers;


import com.example.oauthlogin.exeptions.UserAlreadyExistException;
import com.example.oauthlogin.model.UserDto;
import com.example.oauthlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(final Model model) {
        model.addAttribute("userData", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid UserDto userData, final BindingResult bindingResult, final Model model) {
        if (!Objects.equals(userData.getPasswordSecondTime(), userData.getPassword())) {
            bindingResult.addError(new ObjectError("globalError", "Passwords dont match."));
        }

        if (!userData.isAgreeWithTerms()) {
            bindingResult.addError(new ObjectError("error", "Must agree with terms"));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userData);
            return "register";
        }

        try {
            userService.register(userData);
        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("username", "userData.username", "An account already exists for this username.");
            model.addAttribute("registrationForm", userData);
            return "register";
        }
        return "home";
    }
}

