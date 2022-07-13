package com.example.oauthlogin.service;


import com.example.oauthlogin.exeptions.UserAlreadyExistException;
import com.example.oauthlogin.model.UserDto;

public interface UserService {
    void register(UserDto userData) throws UserAlreadyExistException;
}
