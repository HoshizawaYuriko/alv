package com.example.alv.domain.anime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// Season Value Object
@Embeddable
public class Season {
    @Column(name = "premiered_season")
    private String value;

    protected Season() {}

    public Season(String value) {
        // Check Season Format: <Season> + <4-digit Year>
        if (!value.matches("(Spring|Summer|Fall|Winter) \\d{4}")) {
            throw new IllegalArgumentException("Invalid season format: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Season season)) return false;
        return value.equals(season.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
