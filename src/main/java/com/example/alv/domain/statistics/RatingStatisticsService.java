package com.example.alv.domain.statistics;

import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.anime.Genre;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class RatingStatisticsService {
    // Calculate the average rating using all listentries
    public double calculateOverallAverageRating(List<Listentry> listentries) {
        return listentries.stream()
            .map(Listentry::getRating)
            .filter(r -> r != null && r.getValue() != null)
            .mapToInt(r -> r.getValue())
            .average()
            .orElse(0.0);
    }

    // Get the rating of all listentries and calculate the average by Genre
    public Map<String, Double> calculateAverageRatingPerGenre(List<Listentry> listentries) {
        Map<String, List<Integer>> ratingsPerGenre = new HashMap<>();

        for (Listentry entry : listentries) {
            if (entry.getRating() != null && entry.getRating().getValue() != null) {
                for (Genre genre : entry.getAnime().getGenres()) {
                    ratingsPerGenre
                        .computeIfAbsent(genre.getName(), g -> new ArrayList<>())
                        .add(entry.getRating().getValue());
                }
            }
        }

        Map<String, Double> result = new HashMap<>();
        for (var e : ratingsPerGenre.entrySet()) {
            double avg = e.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0);
            result.put(e.getKey(), avg);
        }

        return result;
    }
}
