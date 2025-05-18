package com.example.alv.application;

import com.example.alv.api.dto.ListentryDTO;
import com.example.alv.domain.anime.Anime;
import com.example.alv.domain.anime.AnimeRepository;
import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.listentry.ListentryRepository;
import com.example.alv.domain.listentry.Status;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ListentryService {
    private final ListentryRepository listentryRepository;
    private final AnimeRepository animeRepository;

    public ListentryService(ListentryRepository listentryRepository, AnimeRepository animeRepository) {
        this.listentryRepository = listentryRepository;
        this.animeRepository = animeRepository;
    }

    // Get all listentries
    public List<Listentry> getAllListentries() {
        return listentryRepository.findAll();
    }

    // Get listentry by ID
    public Listentry getListentryById(Long id) {
        return listentryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listentry not found with id: " + id));
    }

    // Create new listentry
    public Listentry createListentry(ListentryDTO dto) {
        // Find and check if Anime exists
        Anime anime = animeRepository.findById(dto.getAnimeId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found with id: " + dto.getAnimeId()));

        // Check if listentry for the Anime doesn't already exist
        if (listentryRepository.existsByAnime(anime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Listentry for this Anime already exists");
        }

        Listentry listentry = new Listentry();
        listentry.setAnime(anime);
        listentry.setStatus(dto.getStatus());
        listentry.setProgress(dto.getProgress());
        listentry.setStartdate(dto.getStartdate());
        listentry.setEnddate(dto.getEnddate());
        listentry.setRating(dto.getRating());

        listentry.validateInitialState();

        return listentryRepository.save(listentry);
    }

    // Increase progress by 1
    public Listentry incrementProgress(Long id) {
        Listentry listentry = getListentryById(id);
        listentry.incrementProgress();
        return listentryRepository.save(listentry);
    }

    // Set progress with the given number
    public Listentry setProgress(Long id, int newProgress) {
        Listentry listentry = getListentryById(id);
        listentry.updateProgress(newProgress);
        return listentryRepository.save(listentry);
    }

    // Update listentry status
    public Listentry updateStatus(Long id, Status status) {
        Listentry listentry = getListentryById(id);
        listentry.updateStatus(status);
        return listentryRepository.save(listentry);
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
