package com.example.alv.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.alv.application.StatisticsService;
import com.example.alv.domain.listentry.Status;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // Get the Top 10 most watched Genres
    @GetMapping("/top-10-genres")
    public ResponseEntity<List<Map.Entry<String, Long>>> getTopGenres() {
        return ResponseEntity.ok(statisticsService.getTop10Genres());
    }

    // Get amount of listentries by watch status
    @GetMapping("/watch-status-count")
    public ResponseEntity<Map<Status, Long>> getListentryCountByStatus() {
        return ResponseEntity.ok(statisticsService.getListentryCountByStatus());
    }

    // Get total amount of watched Episodes
    @GetMapping("/total-watched-episodes")
    public ResponseEntity<Long> getTotalWatchedEpisodes() {
        long total = statisticsService.getTotalWatchedEpisodes();
        return ResponseEntity.ok(total);
    }
}
