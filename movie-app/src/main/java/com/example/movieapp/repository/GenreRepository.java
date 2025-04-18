package com.example.movieapp.repository;

import com.example.movieapp.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Optional<Genre> findBySlug(String slug);
}