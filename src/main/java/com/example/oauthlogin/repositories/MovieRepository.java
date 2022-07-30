package com.example.oauthlogin.repositories;

import com.example.oauthlogin.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MovieRepository extends JpaRepository<Movie, String> {

}
