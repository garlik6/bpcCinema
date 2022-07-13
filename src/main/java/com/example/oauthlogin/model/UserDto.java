package com.example.oauthlogin.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    @NotEmpty(message = "Username can not be empty")
    private String username;
    @NotEmpty(message = "Email can not be empty")
    private String email;
    @NotEmpty(message = "Password can not be empty")
    private String password;
    @NotEmpty(message = "Password can not be empty")
    private String passwordSecondTime;
    private boolean isAgreeWithTerms;
}
