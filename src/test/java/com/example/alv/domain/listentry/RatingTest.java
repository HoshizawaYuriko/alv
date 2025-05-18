package com.example.alv.domain.listentry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RatingTest {
    @Test
    void shouldThrowIfRatingBelow1() {
        assertThrows(IllegalArgumentException.class, () -> new Rating(0));
    }

    @Test
    void shouldThrowIfRatingAbove10() {
        assertThrows(IllegalArgumentException.class, () -> new Rating(11));
    }

    @Test
    void shouldAllowNullRating() {
        Rating rating = new Rating(null);
        assertFalse(rating.isPresent());
        assertNull(rating.getValue());
    }
}
