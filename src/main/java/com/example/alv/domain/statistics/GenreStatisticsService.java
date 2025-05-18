package com.example.alv.domain.statistics;

import com.example.alv.domain.anime.Genre;
import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.listentry.Status;

import java.util.*;
import java.util.stream.Collectors;

public class GenreStatisticsService {
    // Get the Top 10 most watched Genres
    public List<Map.Entry<String, Long>> top10Genres(List<Listentry> listentries) {
        Map<String, Long> genreCount = new HashMap<>();

        // Count genres of all Animes in the listentries
        for (Listentry entry : listentries) {
            // Exclude listentries on status PLANNED
            if (entry.getStatus() != Status.PLANNED) {
                for (Genre genre : entry.getAnime().getGenres()) {
                    genreCount.merge(genre.getName(), 1L, Long::sum);
                }
            }
        }

        // Sort by amount desc and only return the top 10
        return genreCount.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(10)
            .collect(Collectors.toList());
    }
}
