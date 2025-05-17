package com.example.alv.service;

import com.example.alv.dto.AnimeDTO;
import com.example.alv.model.Anime;
import com.example.alv.model.Genre;
import com.example.alv.repository.AnimeRepository;
import com.example.alv.repository.GenreRepository;
import com.example.alv.repository.ListentryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final GenreRepository genreRepository;
    private final ListentryRepository listentryRepository;

    public AnimeService(AnimeRepository animeRepository, GenreRepository genreRepository, ListentryRepository listentryRepository) {
        this.animeRepository = animeRepository;
        this.genreRepository = genreRepository;
        this.listentryRepository = listentryRepository;
    }

    // Create new Anime entity
    public Anime createAnime(AnimeDTO dto) {
        Set<Genre> genres = new HashSet<>();

        // Check if Genre exists
        for (Long genreId : dto.getGenreIds()) {
            Optional<Genre> genre = genreRepository.findById(genreId);
            if (genre.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre not found with id: " + genreId);
            }
            genres.add(genre.get());
        }

        Anime anime = new Anime();
        anime.setName(dto.getName());
        anime.setMaxEpisodes(dto.getMaxEpisodes());
        anime.setGenres(genres);
        anime.setPremieredSeason(dto.getPremieredSeason());

        return animeRepository.save(anime);
    }

    // Delete Anime entity
    public void deleteAnime(Long animeId) {
        // Find and check if Anime exists
        Anime anime = animeRepository.findById(animeId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found with id: " + animeId));

        // Check if Anime is referenced by any listentries
        boolean hasListentries = listentryRepository.existsByAnime(anime);
        if (hasListentries) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete anime. It is referenced by existing listentries.");
        }

        animeRepository.delete(anime);
    }
}
