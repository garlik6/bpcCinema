package com.example.oauthlogin.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    private int id;
    private String username;
    private String password;
    private UserType userType;
    private String Email;
    private String profilePicUrl;
}
