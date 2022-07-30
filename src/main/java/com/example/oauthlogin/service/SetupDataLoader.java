package com.example.oauthlogin.service;

import com.example.oauthlogin.model.Role;
import com.example.oauthlogin.model.User;
import com.example.oauthlogin.model.UserType;
import com.example.oauthlogin.repositories.RoleRepository;
import com.example.oauthlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();



        if (!userRepository.existsUserByUsername("Admin")) {
            User user = new User();
            user.setUsername("Admin");
            user.setPassword(passwordEncoder.encode("Admin"));
            user.setEmail("test@test.com");
            user.setUserType(UserType.PASSWORD);
            user.setRoles(Collections.singletonList(adminRole));
            userRepository.save(user);
        }
        alreadySetup = true;
    }

    @Transactional
     public void createRoleIfNotFound(String name) {

        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            roleRepository.save(new Role(name));
        }
    }
}
