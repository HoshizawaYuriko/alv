package com.example.alv.domain.statistics;

import com.example.alv.domain.listentry.Listentry;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProgressStatisticsService {
    // Get total amount of watched Episodes
    public long totalWatchedEpisodes(List<Listentry> listentries) {
        return listentries.stream()
            .mapToLong(Listentry::getProgress)
            .sum();
    }
}
