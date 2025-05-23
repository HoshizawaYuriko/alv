package com.example.alv.domain.anime;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "anime")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int maxEpisodes;

    @ManyToMany
    @JoinTable(
        name = "anime_genre",
        joinColumns = @JoinColumn(name = "anime_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @Column
    @Embedded
    private Season premieredSeason;

    // Operations for Genre to add to or remove from an Anime entity
    public void assignGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void addGenre(Genre genre) {
        if (genres.contains(genre)) {
            throw new IllegalStateException("Genre already assigned.");
        }
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }
}
