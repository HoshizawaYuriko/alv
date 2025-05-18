package com.example.alv.domain.listentry;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

// Rating Value Object
@Embeddable
@EqualsAndHashCode
public class Rating {
    @Column(name = "rating")
    private Integer value;

    protected Rating() {}

    public Rating(Integer value) {
        // Rating must be between 1 and 10 (can also be null if no rating was given)
        if (value != null && (value < 1 || value > 10)) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "unrated";
    }
}
