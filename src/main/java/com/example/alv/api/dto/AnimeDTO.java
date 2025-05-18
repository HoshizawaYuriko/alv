package com.example.alv.api.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimeDTO {
    private String name;
    private int maxEpisodes;
    private Set<Long> genreIds;
    private String premieredSeason;
}
