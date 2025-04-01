package com.example.movieapp.api;

import com.example.movieapp.entity.Favorite;
import com.example.movieapp.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<Favorite> addFavorite(
            @RequestParam Integer userId,
            @RequestParam Integer movieId) {
        Favorite favorite = favoriteService.addFavorite(userId, movieId);
        return ResponseEntity.ok(favorite);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Favorite>> getFavorites(@PathVariable Integer userId) {
        List<Favorite> favorites = favoriteService.getFavoritesByUser(userId);
        return ResponseEntity.ok(favorites);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFavorite(
            @RequestParam Integer userId,
            @RequestParam Integer movieId) {
        favoriteService.removeFavorite(userId, movieId);
        return ResponseEntity.ok("Đã xóa phim yêu thích.");
    }

    @DeleteMapping("/removeAll/{userId}")
    public ResponseEntity<String> removeAllFavorites(@PathVariable Integer userId) {
        favoriteService.removeAllFavorites(userId);
        return ResponseEntity.ok("Đã xóa toàn bộ phim yêu thích.");
    }
}
