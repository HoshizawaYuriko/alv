package com.example.alv.api;

import com.example.alv.api.dto.AnimeDTO;
import com.example.alv.application.AnimeService;
import com.example.alv.domain.anime.Anime;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    // Get all Animes
    @GetMapping("/list")
    public ResponseEntity<List<Anime>> getAllAnimes() {
        return ResponseEntity.ok(animeService.getAllAnimes());
    }

    // Get Anime by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.getAnimeById(id));
    }

    // Create a new Anime from given form data
    @PostMapping("/create")
    public ResponseEntity<Anime> createAnime(@RequestBody AnimeDTO dto) {
        Anime anime = animeService.createAnime(dto);
        return ResponseEntity.ok(anime);
    }

    // Delete an existing Anime by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        animeService.deleteAnime(id);
        return ResponseEntity.noContent().build();
    }
}
