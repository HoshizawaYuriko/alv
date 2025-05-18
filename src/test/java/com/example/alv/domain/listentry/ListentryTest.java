package com.example.alv.domain.listentry;

import com.example.alv.domain.anime.Anime;
import com.example.alv.domain.anime.Season;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ListentryTest {
    // Progress at 11/12 -> progress is increased by 1 -> Status should automatically swap from WATCHING to COMPLETED
    @Test
    void shouldSetStatusToCompletedWhenProgressEqualsMax() {
        Anime anime = new Anime(1L, "Test Anime", 12, Set.of(), new Season("Spring 2023"));
        Listentry listentry = new ListentryBuilder()
            .withAnime(anime)
            .withStatus(Status.WATCHING)
            .withProgress(11)
            .build();

        listentry.incrementProgress();

        assertEquals(12, listentry.getProgress());
        assertEquals(Status.COMPLETED, listentry.getStatus());
    }

    // Status is at PLANNED and progress at 0 -> progress is increased by 1 -> Status should automatically swap from PLANNED to WATCHING
    @Test
    void shouldSetStatusToWatchingWhenProgressIncreasedFromPlanned() {
        Anime anime = new Anime(1L, "Test Anime", 12, Set.of(), new Season("Winter 2022"));
        Listentry listentry = new ListentryBuilder()
            .withAnime(anime)
            .withStatus(Status.PLANNED)
            .withProgress(0)
            .build();

        listentry.incrementProgress();

        assertEquals(1, listentry.getProgress());
        assertEquals(Status.WATCHING, listentry.getStatus());
    }

    // Status is at COMPLETED and progress is at max episodes -> progress is increased by 1 -> should throw an error
    @Test
    void shouldThrowIfProgressExceedsMax() {
        Anime anime = new Anime(1L, "Test Anime", 12, Set.of(), new Season("Fall 2021"));
        Listentry listentry = new ListentryBuilder()
            .withAnime(anime)
            .withStatus(Status.COMPLETED)
            .withProgress(12)
            .build();

        assertThrows(IllegalArgumentException.class, () -> listentry.incrementProgress());
    }

    // Progress at 10/12 -> Status is manually set from WATCHING to COMPLETED -> should throw an error
    @Test
    void shouldThrowIfStatusIsCompletedButProgressIsNotMax() {
        Anime anime = new Anime(1L, "Test Anime", 12, Set.of(), new Season("Spring 2020"));
        Listentry listentry = new ListentryBuilder()
            .withAnime(anime)
            .withStatus(Status.WATCHING)
            .withProgress(10)
            .build();

        assertThrows(IllegalArgumentException.class, () -> listentry.updateStatus(Status.COMPLETED));
    }

    // Trying to set progress to a negative number -> should throw an error
    @Test
    void shouldThrowWhenSettingNegativeProgress() {
        Anime anime = new Anime(1L, "Negative Test", 10, Set.of(), new Season("Fall 2022"));
        Listentry listentry = new ListentryBuilder()
            .withAnime(anime)
            .withStatus(Status.PLANNED)
            .withProgress(0)
            .build();

        assertThrows(IllegalArgumentException.class, () -> listentry.updateProgress(-1));
    }
}
