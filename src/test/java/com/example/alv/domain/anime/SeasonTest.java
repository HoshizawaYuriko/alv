package com.example.alv.domain.anime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeasonTest {
    @Test
    void shouldCreateValidSeason() {
        Season season = new Season("Spring 2024");
        assertEquals("Spring 2024", season.getValue());
    }

    @Test
    void shouldThrowOnInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new Season("Sprung 2024"));
    }

    @Test
    void shouldThrowOnMissingYear() {
        assertThrows(IllegalArgumentException.class, () -> new Season("Fall"));
    }

    @Test
    void seasonsWithSameValueShouldBeEqual() {
        Season a = new Season("Fall 2022");
        Season b = new Season("Fall 2022");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
