package com.example.alv.domain.anime;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class AnimeTest {
    @Test
    void shouldAssignGenresCorrectly() {
        Genre genre = new Genre(1L, "Action");
        Anime anime = new Anime(1L, "Naruto", 220, new HashSet<>(), new Season("Fall 2002"));

        anime.addGenre(genre);

        assertTrue(anime.getGenres().contains(genre));
    }
}
