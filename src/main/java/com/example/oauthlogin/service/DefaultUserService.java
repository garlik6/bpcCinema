package com.example.oauthlogin.service;


import com.example.oauthlogin.exeptions.UserAlreadyExistException;
import com.example.oauthlogin.model.User;
import com.example.oauthlogin.model.UserDto;
import com.example.oauthlogin.model.UserType;
import com.example.oauthlogin.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service("userService")
public class DefaultUserService implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void register(UserDto userData) throws UserAlreadyExistException {
        if (userRepository.existsUserByUsernameAndUserType(userData.getUsername(), UserType.PASSWORD)) {
            throw new UserAlreadyExistException("User already exists for this username");
        }
        User user = new User();
        BeanUtils.copyProperties(userData,user);
        user.setUserType(UserType.PASSWORD);
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        userRepository.save(user);
    }
}

