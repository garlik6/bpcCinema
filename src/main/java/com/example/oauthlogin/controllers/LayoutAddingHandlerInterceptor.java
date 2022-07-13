package com.example.oauthlogin.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LayoutAddingHandlerInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a.getClass() == OAuth2AuthenticationToken.class) {
            String username = ((OAuth2AuthenticationToken) a).getPrincipal().getAttribute("login");
            modelAndView.addObject("username", username);
            System.out.println(":)");
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
