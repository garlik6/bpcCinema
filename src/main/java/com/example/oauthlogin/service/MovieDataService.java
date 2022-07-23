package com.example.oauthlogin.service;

import com.example.oauthlogin.model.Movie;
import com.example.oauthlogin.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class MovieDataService {

    private final WebClient webClient;

    @Value("${filmApi.key}")
    private String apiKey;

    @Autowired
    public MovieDataService(WebClient webClient, MovieRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    public MovieRepository repository;

    public Movie getMovieByName(final String movieName) {

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apiKey", apiKey)
                        .queryParam("t", movieName)
                        .build())
                .retrieve().bodyToMono(Movie.class)
                .block();
    }

    public void saveMovie(Movie movie) {
        repository.save(movie);
    }
}
