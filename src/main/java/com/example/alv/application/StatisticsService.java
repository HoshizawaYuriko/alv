package com.example.alv.application;

import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.listentry.ListentryRepository;
import com.example.alv.domain.listentry.Status;
import com.example.alv.domain.statistics.GenreStatisticsService;
import com.example.alv.domain.statistics.ProgressStatisticsService;
import com.example.alv.domain.statistics.StatusStatisticsService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    private final ListentryRepository listentryRepository;
    private final GenreStatisticsService genreStatisticsService;
    private final StatusStatisticsService statusStatisticsService;
    private final ProgressStatisticsService progressStatisticsService;

    public StatisticsService(
        ListentryRepository listentryRepository,
        GenreStatisticsService genreStatisticsService,
        StatusStatisticsService statusStatisticsService,
        ProgressStatisticsService progressStatisticsService
    ) {
        this.listentryRepository = listentryRepository;
        this.genreStatisticsService = genreStatisticsService;
        this.statusStatisticsService = statusStatisticsService;
        this.progressStatisticsService = progressStatisticsService;
    }

    // Get the Top 10 most watched Genres
    public List<Map.Entry<String, Long>> getTop10Genres() {
        List<Listentry> listentries = listentryRepository.findAll();
        return genreStatisticsService.top10Genres(listentries);
    }

    // Get total amount of listentries by status
    public Map<Status, Long> getListentryCountByStatus() {
        List<Listentry> listentries = listentryRepository.findAll();
        return statusStatisticsService.countByStatus(listentries);
    }

    // Get total amount of watched Episodes
    public long getTotalWatchedEpisodes() {
        List<Listentry> listentries = listentryRepository.findAll();
        return progressStatisticsService.totalWatchedEpisodes(listentries);
    }
}
