package com.example.alv.domain.listentry;

import com.example.alv.domain.anime.Anime;

import java.sql.Timestamp;

public class ListentryBuilder {
    private Anime anime;
    private Status status = Status.PLANNED;
    private int progress = 0;
    private Timestamp startdate = null;
    private Timestamp enddate = null;
    private Rating rating = null;

    public ListentryBuilder withAnime(Anime anime) {
        this.anime = anime;
        return this;
    }

    public ListentryBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ListentryBuilder withProgress(int progress) {
        this.progress = progress;
        return this;
    }

    public ListentryBuilder withStartDate(Timestamp startdate) {
        this.startdate = startdate;
        return this;
    }

    public ListentryBuilder withEndDate(Timestamp enddate) {
        this.enddate = enddate;
        return this;
    }

    public ListentryBuilder withRating(Integer value) {
        this.rating = new Rating(value);
        return this;
    }

    public Listentry build() {
        return new Listentry(null, anime, status, progress, startdate, enddate, rating);
    }
}
