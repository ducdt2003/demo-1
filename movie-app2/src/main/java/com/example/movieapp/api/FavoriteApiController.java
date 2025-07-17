package com.example.movieapp.api;

import com.example.movieapp.entity.Favorite;
import com.example.movieapp.service.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/favorties")
public class FavoriteApiController {

    private final FavoriteService favoriteService;

    // Để test, ta fix userId = 1
    private static final Integer FIXED_USER_ID = 1;

    public FavoriteApiController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * GET /api/favorties?page=1&pageSize=10
     * Lấy danh sách phim yêu thích của user với phân trang.
     */
    @GetMapping
    public ResponseEntity<Page<Favorite>> getFavorites(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Favorite> favorites = favoriteService.getFavoritesByUser(FIXED_USER_ID, page, pageSize);
        return ResponseEntity.ok(favorites);
    }

    /**
     * POST /api/favorties/add
     * Body JSON: { "movieId": 123 }
     * Thêm phim vào yêu thích cho user.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestBody Map<String, Integer> payload) {
        Integer movieId = payload.get("movieId");
        if (movieId == null) {
            return ResponseEntity.badRequest().body("movieId is required");
        }
        Favorite favorite = favoriteService.addFavorite(FIXED_USER_ID, movieId);
        if (favorite != null) {
            return ResponseEntity.ok(favorite);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie or User not found");
        }
    }

    /**
     * DELETE /api/favorties/remove
     * Body JSON: { "movieId": 123 }
     * Xóa phim khỏi danh sách yêu thích của user.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(@RequestBody Map<String, Integer> payload) {
        Integer movieId = payload.get("movieId");
        if (movieId == null) {
            return ResponseEntity.badRequest().body("movieId is required");
        }
        boolean removed = favoriteService.removeFavorite(FIXED_USER_ID, movieId);
        if (removed) {
            return ResponseEntity.ok("Favorite removed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite not found.");
        }
    }

    /**
     * DELETE /api/favorties/removeAll
     * Xóa tất cả phim yêu thích của user.
     */
    @DeleteMapping("/removeAll")
    public ResponseEntity<?> removeAllFavorites() {
        favoriteService.removeAllFavorites(FIXED_USER_ID);
        return ResponseEntity.ok("All favorite movies removed for user " + FIXED_USER_ID);
    }
}



