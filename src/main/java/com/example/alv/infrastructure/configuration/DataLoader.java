package com.example.alv.infrastructure.configuration;

import com.example.alv.domain.anime.Anime;
import com.example.alv.domain.anime.AnimeRepository;
import com.example.alv.domain.anime.Genre;
import com.example.alv.domain.anime.GenreRepository;
import com.example.alv.domain.anime.Season;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Initializes Data for the Database
@Component
public class DataLoader implements CommandLineRunner {
    private final GenreRepository genreRepository;
    private final AnimeRepository animeRepository;

    public DataLoader(GenreRepository genreRepository, AnimeRepository animeRepository) {
        this.genreRepository = genreRepository;
        this.animeRepository = animeRepository;
    }

    @Override
    public void run(String... args) {
        loadGenres();
        loadAnimes();
    }

    // Generate Genres
    private void loadGenres() {
        if (genreRepository.count() == 0) {
            List<Genre> genres = List.of(
                new Genre(null, "Action"),
                new Genre(null, "Adventure"),
                new Genre(null, "Comedy"),
                new Genre(null, "Drama"),
                new Genre(null, "Fantasy"),
                new Genre(null, "Horror"),
                new Genre(null, "Sci-Fi"),
                new Genre(null, "Slice of Life"),
                new Genre(null, "Romance"),
                new Genre(null, "Mystery")
            );
            genreRepository.saveAll(genres);
        }
    }

    // Generate Animes
    private void loadAnimes() {
        if (animeRepository.count() == 0) {
            // Retrieve all genres after they have been saved
            List<Genre> genres = genreRepository.findAll();
            Map<String, Genre> genreMap = genres.stream()
                .collect(Collectors.toMap(Genre::getName, genre -> genre));

            List<Anime> animes = List.of(
                new Anime(null, "Attack on Titan", 25, Set.of(genreMap.get("Action"), genreMap.get("Drama"), genreMap.get("Fantasy")), new Season("Spring 2013")),
                new Anime(null, "One Piece", 2000, Set.of(genreMap.get("Action"), genreMap.get("Adventure"), genreMap.get("Comedy")), new Season("Fall 1999")),
                new Anime(null, "Demon Slayer", 26, Set.of(genreMap.get("Action"), genreMap.get("Fantasy")), new Season("Spring 2019")),
                new Anime(null, "Steins;Gate", 24, Set.of(genreMap.get("Sci-Fi"), genreMap.get("Drama")), new Season("Spring 2011")),
                new Anime(null, "Your Lie in April", 22, Set.of(genreMap.get("Drama"), genreMap.get("Romance")), new Season("Fall 2014")),
                new Anime(null, "Re:ZERO -Starting Life in Another World-", 25, Set.of(genreMap.get("Fantasy"), genreMap.get("Drama")), new Season("Spring 2016")),
                new Anime(null, "My Hero Academia", 13, Set.of(genreMap.get("Action"), genreMap.get("Adventure")), new Season("Spring 2016")),
                new Anime(null, "My Hero Academia Season 2", 25, Set.of(genreMap.get("Action"), genreMap.get("Adventure")), new Season("Spring 2017")),
                new Anime(null, "Cowboy Bebop", 26, Set.of(genreMap.get("Action"), genreMap.get("Sci-Fi")), new Season("Spring 1998")),
                new Anime(null, "Clannad", 23, Set.of(genreMap.get("Slice of Life"), genreMap.get("Romance")), new Season("Fall 2007"))
            );

            animeRepository.saveAll(animes);
        }
    }
}
