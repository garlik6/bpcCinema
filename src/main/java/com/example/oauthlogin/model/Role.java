package com.example.oauthlogin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "users_roles",
             inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Collection<User> users;

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }
}
