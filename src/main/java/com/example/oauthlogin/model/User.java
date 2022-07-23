package com.example.oauthlogin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

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


    @ManyToMany
    @JoinTable(
            name = "users_movies",
            joinColumns = @JoinColumn(
                    name = "user_Id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "movie_id", referencedColumnName = "imdbID"))
    private Collection<Movie> movies;

    public void addMovie(Movie movie){
        if(isMovieLiked(movie))
            return;
        movies.add(movie);
    }

    public void removeMovie(Movie movie){
        movies.removeIf(a -> Objects.equals(a.getImdbID(), movie.getImdbID()));
    }


    public Boolean isMovieLiked(Movie movie) {
        return movies.stream().anyMatch(movie1 -> Objects.equals(movie1.getImdbID(), movie.getImdbID()));
    }
}
