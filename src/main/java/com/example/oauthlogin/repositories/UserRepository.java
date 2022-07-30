package com.example.oauthlogin.repositories;

import com.example.oauthlogin.model.User;
import com.example.oauthlogin.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(int id);
    Boolean existsUserByUsernameAndUserType(String name, UserType userType);
    Boolean existsUserById(int id);
    boolean existsUserByUsername(String name);
}
