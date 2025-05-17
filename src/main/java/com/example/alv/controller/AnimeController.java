package com.example.alv.controller;

import com.example.alv.dto.AnimeDTO;
import com.example.alv.model.Anime;
import com.example.alv.service.AnimeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
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
