package com.example.alv.config;

import com.example.alv.model.Anime;
import com.example.alv.model.Genre;
import com.example.alv.repository.AnimeRepository;
import com.example.alv.repository.GenreRepository;
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
                new Anime(null, "Attack on Titan", 75, Set.of(genreMap.get("Action"), genreMap.get("Drama"), genreMap.get("Fantasy")), "Winter 2013"),
                new Anime(null, "One Piece", 1000, Set.of(genreMap.get("Action"), genreMap.get("Adventure"), genreMap.get("Comedy")), "Fall 1999"),
                new Anime(null, "Demon Slayer", 26, Set.of(genreMap.get("Action"), genreMap.get("Fantasy")), "Spring 2019"),
                new Anime(null, "Steins;Gate", 24, Set.of(genreMap.get("Sci-Fi"), genreMap.get("Drama")), "Spring 2011"),
                new Anime(null, "Your Lie in April", 22, Set.of(genreMap.get("Drama"), genreMap.get("Romance")), "Fall 2014"),
                new Anime(null, "Re:Zero", 50, Set.of(genreMap.get("Fantasy"), genreMap.get("Drama")), "Spring 2016"),
                new Anime(null, "My Hero Academia", 138, Set.of(genreMap.get("Action"), genreMap.get("Adventure")), "Spring 2016"),
                new Anime(null, "Cowboy Bebop", 26, Set.of(genreMap.get("Action"), genreMap.get("Sci-Fi")), "Spring 1998"),
                new Anime(null, "Clannad", 44, Set.of(genreMap.get("Slice of Life"), genreMap.get("Romance")), "Fall 2007")
            );

            animeRepository.saveAll(animes);
        }
    }
}
