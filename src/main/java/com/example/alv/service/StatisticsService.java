package com.example.alv.service;

import com.example.alv.model.Genre;
import com.example.alv.model.Listentry;
import com.example.alv.model.Status;
import com.example.alv.repository.ListentryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private final ListentryRepository listentryRepository;

    public StatisticsService(ListentryRepository listentryRepository) {
        this.listentryRepository = listentryRepository;
    }

    // Get the Top 10 most watched Genres
    public List<Map.Entry<String, Long>> getTop10Genres() {
        List<Listentry> listentries = listentryRepository.findAll();

        Map<String, Long> genreCount = new HashMap<>();

        // Count genres of all Animes in the listentries
        for (Listentry entry : listentries) {
            // Exclude listentries on status PLANNED
            if (entry.getStatus() != Status.PLANNED) {
                Set<Genre> genres = entry.getAnime().getGenres();
                for (Genre genre : genres) {
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

    // Get total amount of listentries by status
    public Map<Status, Long> getListentryCountByStatus() {
        List<Listentry> listentries = listentryRepository.findAll();

        Map<Status, Long> counts = new EnumMap<>(Status.class);

        // Initialize all statuses with zero
        for (Status status : Status.values()) {
            counts.put(status, 0L);
        }

        // Count listentries that match the status
        for (Listentry entry : listentries) {
            counts.merge(entry.getStatus(), 1L, Long::sum);
        }

        return counts;
    }

    // Get total amount of watched Episodes
    public long getTotalWatchedEpisodes() {
        return listentryRepository.findAll()
            .stream()
            .mapToLong(Listentry::getProgress)
            .sum();
    }
}
