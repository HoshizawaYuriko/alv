package com.example.alv.application;

import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.listentry.ListentryRepository;
import com.example.alv.domain.listentry.Status;
import com.example.alv.domain.statistics.GenreStatisticsService;
import com.example.alv.domain.statistics.ProgressStatisticsService;
import com.example.alv.domain.statistics.RatingStatisticsService;
import com.example.alv.domain.statistics.StatusStatisticsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticsServiceTest {
    private ListentryRepository listentryRepository;
    private GenreStatisticsService genreStatisticsService;
    private StatusStatisticsService statusStatisticsService;
    private ProgressStatisticsService progressStatisticsService;
    private RatingStatisticsService ratingStatisticsService;
    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        listentryRepository = mock(ListentryRepository.class);
        genreStatisticsService = mock(GenreStatisticsService.class);
        statusStatisticsService = mock(StatusStatisticsService.class);
        progressStatisticsService = mock(ProgressStatisticsService.class);
        ratingStatisticsService = mock(RatingStatisticsService.class);

        statisticsService = new StatisticsService(
            listentryRepository,
            genreStatisticsService,
            statusStatisticsService,
            progressStatisticsService,
            ratingStatisticsService
        );
    }

    @Test
    void shouldReturnTop10Genres() {
        List<Listentry> list = List.of(mock(Listentry.class));
        when(listentryRepository.findAll()).thenReturn(list);
        when(genreStatisticsService.top10Genres(list)).thenReturn(
            List.of(Map.entry("Action", 5L), Map.entry("Drama", 3L))
        );

        var result = statisticsService.getTop10Genres();

        assertEquals(2, result.size());
        assertEquals("Action", result.get(0).getKey());
        verify(genreStatisticsService).top10Genres(list);
    }

    @Test
    void shouldReturnStatusCounts() {
        List<Listentry> list = List.of(mock(Listentry.class));
        when(listentryRepository.findAll()).thenReturn(list);
        when(statusStatisticsService.countByStatus(list)).thenReturn(Map.of(Status.WATCHING, 2L));

        var result = statisticsService.getListentryCountByStatus();

        assertEquals(1, result.size());
        assertEquals(2L, result.get(Status.WATCHING));
        verify(statusStatisticsService).countByStatus(list);
    }

    @Test
    void shouldReturnTotalWatchedEpisodes() {
        List<Listentry> list = List.of(mock(Listentry.class));
        when(listentryRepository.findAll()).thenReturn(list);
        when(progressStatisticsService.totalWatchedEpisodes(list)).thenReturn(42L);

        long result = statisticsService.getTotalWatchedEpisodes();

        assertEquals(42L, result);
        verify(progressStatisticsService).totalWatchedEpisodes(list);
    }
}
