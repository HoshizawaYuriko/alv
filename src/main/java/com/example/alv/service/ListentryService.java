package com.example.alv.service;

import com.example.alv.dto.ListentryDTO;
import com.example.alv.model.Anime;
import com.example.alv.model.Status;
import com.example.alv.model.Listentry;
import com.example.alv.repository.AnimeRepository;
import com.example.alv.repository.ListentryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ListentryService {
    private final ListentryRepository listentryRepository;
    private final AnimeRepository animeRepository;

    public ListentryService(ListentryRepository listentryRepository, AnimeRepository animeRepository) {
        this.listentryRepository = listentryRepository;
        this.animeRepository = animeRepository;
    }

    // Create new listentry
    public Listentry createListentry(ListentryDTO dto) {
        // Find and check if Anime exists
        Anime anime = animeRepository.findById(dto.getAnimeId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Anime not found with id: " + dto.getAnimeId()));

        // Check if progress doesn't exceed maxEpisodes
        if (dto.getProgress() > anime.getMaxEpisodes()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Progress cannot be greater than max episode count of the Anime (" + anime.getMaxEpisodes() + ")"
            );
        }

        // Check if start and end dates are valid
        if (dto.getStartdate() != null && dto.getEnddate() != null && dto.getEnddate().before(dto.getStartdate())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "End date cannot be before start date."
            );
        }

        // Check status vs progress consistency
        if (Status.PLANNED == dto.getStatus() && dto.getProgress() != 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Status is PLANNED but progress is higher than 0."
            );
        }
        if (Status.COMPLETED == dto.getStatus() && dto.getProgress() < anime.getMaxEpisodes()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Status is COMPLETED but progress is less than max episode count."
            );
        }
        if (Status.ON_HOLD == dto.getStatus() && dto.getProgress() == anime.getMaxEpisodes()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Status is ON_HOLD but all Episodes have already been watched."
            );
        }
        if (Status.DROPPED == dto.getStatus() && dto.getProgress() == anime.getMaxEpisodes()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Status is DROPPED but all Episodes have already been watched."
            );
        }

        Listentry listentry = new Listentry();
        listentry.setAnime(anime);
        listentry.setStatus(dto.getStatus());
        listentry.setProgress(dto.getProgress());
        listentry.setStartdate(dto.getStartdate());
        listentry.setEnddate(dto.getEnddate());
        listentry.setRating(dto.getRating());

        return listentryRepository.save(listentry);
    }

    // Increase progress by 1
    public Listentry incrementProgress(Long id) {
        // Find existing Anime
        Listentry entry = listentryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listentry not found"));

        // Get current Episode count and max Episode count
        int currentEpisode = entry.getProgress();
        int maxEpisode = entry.getAnime().getMaxEpisodes();

        // Check if max Episode would be exceeded
        if (currentEpisode >= maxEpisode) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already at max episode count");
        }

        entry.setProgress(currentEpisode + 1);

        return listentryRepository.save(entry);
    }

    // Set progress with the given number
    public Listentry setProgress(Long id, int newProgress) {
        // Find existing Anime
        Listentry entry = listentryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listentry not found"));

        // Get max Episode count
        int max = entry.getAnime().getMaxEpisodes();

        // Check if given progress is valid
        if (newProgress < 0 || newProgress > max) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Progress must be between 0 and " + max);
        }

        entry.setProgress(newProgress);

        return listentryRepository.save(entry);
    }

    // Delete an existing listentry
    public void deleteListentry(Long id) {
        // Check if listentry exists
        if (!listentryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Listentry not found");
        }

        listentryRepository.deleteById(id);
    }
}
