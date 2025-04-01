package com.example.movieapp.service;

import com.example.movieapp.entity.Favorite;
import com.example.movieapp.entity.Movie;
import com.example.movieapp.entity.User;
import com.example.movieapp.repository.FavoriteRepository;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public Favorite addFavorite(Integer userId, Integer movieId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Movie> movieOpt = movieRepository.findById(movieId);

        if (userOpt.isPresent() && movieOpt.isPresent()) {
            Favorite favorite = Favorite.builder()
                    .user(userOpt.get())
                    .movie(movieOpt.get())
                    .build();
            return favoriteRepository.save(favorite);
        }
        throw new RuntimeException("User or Movie not found");
    }

    public List<Favorite> getFavoritesByUser(Integer userId) {
        return favoriteRepository.findByUserId(userId);
    }

    @Transactional
    public void removeFavorite(Integer userId, Integer movieId) {
        favoriteRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    public void removeAllFavorites(Integer userId) {
        favoriteRepository.deleteByUserId(userId);
    }
}
