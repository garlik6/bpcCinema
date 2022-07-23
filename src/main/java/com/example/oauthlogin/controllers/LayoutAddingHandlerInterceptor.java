package com.example.oauthlogin.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@Component
public class LayoutAddingHandlerInterceptor implements HandlerInterceptor {

    @Value("${profilePicture.path}")
    private String profilePicturePath;
    @Value("${profilePicture.format}")
    private String ProfilePictureFormat;

    @Override
    public void postHandle(@NotNull HttpServletRequest  request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

    if(modelAndView != null){
        String username;
        if (a.getClass() == OAuth2AuthenticationToken.class) {
            username = ((OAuth2AuthenticationToken) a).getPrincipal().getAttribute("login");

        } else {
            username = a.getName();
        }
        modelAndView.addObject("username", username);
        String HtmlPathToPic = "/images/ProfilePic/" + username + "_pic." + ProfilePictureFormat;
        modelAndView.addObject("profilePicture", HtmlPathToPic);
    }


        if (a.getClass() == AnonymousAuthenticationToken.class){
            a.setAuthenticated(false);
        }
//        System.out.println(request);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
