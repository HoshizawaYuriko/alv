package com.example.alv.application;

import com.example.alv.api.dto.ListentryDTO;
import com.example.alv.domain.anime.Anime;
import com.example.alv.domain.anime.Season;
import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.listentry.ListentryRepository;
import com.example.alv.domain.listentry.Rating;
import com.example.alv.domain.listentry.Status;
import com.example.alv.domain.anime.AnimeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ListentryServiceTest {
    private AnimeRepository animeRepository;
    private ListentryRepository listentryRepository;
    private ListentryService listentryService;

    @BeforeEach
    void setUp() {
        // Create mock classes
        animeRepository = mock(AnimeRepository.class);
        listentryRepository = mock(ListentryRepository.class);
        listentryService = new ListentryService(listentryRepository, animeRepository);
    }

    @Test
    void shouldCreateListentryWithValidDTO() {
        // Arrange
        Anime anime = new Anime(1L, "Naruto", 220, Set.of(), new Season("Fall 2002"));

        ListentryDTO dto = new ListentryDTO();
        dto.setAnimeId(1L);
        dto.setStatus(Status.PLANNED);
        dto.setProgress(0);
        dto.setStartdate(null);
        dto.setEnddate(null);
        dto.setRating(null);

        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(listentryRepository.existsByAnime(anime)).thenReturn(false);
        when(listentryRepository.save(any(Listentry.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Listentry result = listentryService.createListentry(dto);

        // Assert
        assertNotNull(result);
        assertEquals(anime, result.getAnime());
        assertEquals(Status.PLANNED, result.getStatus());
        assertEquals(0, result.getProgress());

        // Verify interaction
        verify(animeRepository).findById(1L);
        verify(listentryRepository).existsByAnime(anime);
        verify(listentryRepository).save(any(Listentry.class));
    }

    @Test
    void shouldReturnEntriesByStatus() {
        Listentry listentry = mock(Listentry.class);
        when(listentryRepository.findByStatus(Status.WATCHING)).thenReturn(List.of(listentry));

        List<Listentry> result = listentryService.findByStatus(Status.WATCHING);

        assertEquals(1, result.size());
        verify(listentryRepository).findByStatus(Status.WATCHING);
    }

    @Test
    void shouldReturnEntriesByGenre() {
        Listentry listentry = mock(Listentry.class);
        when(listentryRepository.findByGenreName("Action")).thenReturn(List.of(listentry));

        List<Listentry> result = listentryService.findByGenre("Action");

        assertEquals(1, result.size());
        verify(listentryRepository).findByGenreName("Action");
    }

    @Test
    void shouldReturnEntriesByMinimumRating() {
        Listentry listentry = new Listentry();
        listentry.setRating(new Rating(9));

        when(listentryRepository.findByRatingGreaterThan(8)).thenReturn(List.of(listentry));

        List<Listentry> result = listentryService.findByRatingGreaterThan(8);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getRating().getValue() > 8);
        verify(listentryRepository).findByRatingGreaterThan(8);
    }
}
