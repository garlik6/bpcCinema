package com.example.oauthlogin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue()
    private int id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String Email;
    private String profilePicUrl;


    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "users_movies",
            joinColumns = @JoinColumn(
                    name = "user_Id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "movie_id", referencedColumnName = "imdbID")

    )
    private Collection<Movie> movies;

    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            },
            fetch = EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    public void addMovie(Movie movie) {
        if (isMovieLiked(movie))
            return;
        movies.add(movie);
    }

    public void removeMovie(Movie movie) {
        movies.removeIf(a -> Objects.equals(a.getImdbID(), movie.getImdbID()));
    }


    public Boolean isMovieLiked(Movie movie) {
        return movies.stream().anyMatch(movie1 -> Objects.equals(movie1.getImdbID(), movie.getImdbID()));
    }
}
