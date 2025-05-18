package com.example.alv.domain.statistics;

import com.example.alv.domain.anime.Anime;
import com.example.alv.domain.anime.Genre;
import com.example.alv.domain.anime.Season;
import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.listentry.Rating;
import com.example.alv.domain.listentry.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RatingStatisticsServiceTest {

    private RatingStatisticsService service;

    @BeforeEach
    void setUp() {
        service = new RatingStatisticsService();
    }

    @Test
    void shouldCalculateAverageRatingPerGenre() {
        Genre action = new Genre(1L, "Action");
        Genre drama = new Genre(2L, "Drama");

        Anime anime1 = new Anime(1L, "Anime 1", 12, Set.of(action), new Season("Spring 2022"));
        Anime anime2 = new Anime(2L, "Anime 2", 24, Set.of(action, drama), new Season("Fall 2023"));
        Anime anime3 = new Anime(3L, "Anime 3", 10, Set.of(drama), new Season("Winter 2021"));

        Listentry entry1 = new Listentry(null, anime1, Status.COMPLETED, 12, null, null, new Rating(8));
        Listentry entry2 = new Listentry(null, anime2, Status.COMPLETED, 24, null, null, new Rating(6));
        Listentry entry3 = new Listentry(null, anime3, Status.COMPLETED, 10, null, null, new Rating(9));

        Map<String, Double> result = service.calculateAverageRatingPerGenre(List.of(entry1, entry2, entry3));

        assertEquals(2, result.size());
        // (8 + 6) / 2
        assertEquals(7.0, result.get("Action"));
        // (6 + 9) / 2
        assertEquals(7.5, result.get("Drama"));
    }

    @Test
    void shouldReturnEmptyMapIfNoRatedEntriesPerGenre() {
        Anime anime = new Anime(1L, "Empty", 10, Set.of(new Genre(1L, "Action")), new Season("Fall 2021"));
        Listentry unratedEntry = new Listentry(null, anime, Status.PLANNED, 0, null, null, new Rating(null));

        Map<String, Double> result = service.calculateAverageRatingPerGenre(List.of(unratedEntry));
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldCalculateOverallAverageRating() {
        Listentry entry1 = new Listentry(null, null, Status.COMPLETED, 0, null, null, new Rating(6));
        Listentry entry2 = new Listentry(null, null, Status.COMPLETED, 0, null, null, new Rating(8));
        Listentry entry3 = new Listentry(null, null, Status.COMPLETED, 0, null, null, new Rating(10));

        double result = service.calculateOverallAverageRating(List.of(entry1, entry2, entry3));

        assertEquals(8.0, result);
    }

    @Test
    void shouldIgnoreNullRatingsInOverallAverage() {
        Listentry rated = new Listentry(null, null, Status.COMPLETED, 0, null, null, new Rating(9));
        Listentry unrated = new Listentry(null, null, Status.COMPLETED, 0, null, null, new Rating(null));

        double result = service.calculateOverallAverageRating(List.of(rated, unrated));

        assertEquals(9.0, result);
    }

    @Test
    void shouldReturnZeroIfNoValidRatings() {
        Listentry entry1 = new Listentry(null, null, Status.PLANNED, 0, null, null, null);
        Listentry entry2 = new Listentry(null, null, Status.PLANNED, 0, null, null, new Rating(null));

        double result = service.calculateOverallAverageRating(List.of(entry1, entry2));

        assertEquals(0.0, result);
    }
}
