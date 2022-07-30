package com.example.oauthlogin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    private String imdbID;
    private String title;
    private String year;
    private String runtime;
    private String genre;
    private String director;
    private String plot;
    private String poster;


    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "users_movies",
            inverseJoinColumns = @JoinColumn(
                    name = "user_Id", referencedColumnName = "id"),
            joinColumns  = @JoinColumn(
                    name = "movie_id", referencedColumnName = "imdbID")

    )
    private Collection<User> users;

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}
