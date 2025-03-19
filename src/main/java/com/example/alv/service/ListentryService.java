package com.example.alv.service;

import com.example.alv.dto.ListentryDTO;
import com.example.alv.model.Anime;
import com.example.alv.model.Listentry;
import com.example.alv.repository.AnimeRepository;
import com.example.alv.repository.ListentryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListentryService {
    private final ListentryRepository listentryRepository;
    private final AnimeRepository animeRepository;

    public ListentryService(ListentryRepository listentryRepository, AnimeRepository animeRepository) {
        this.listentryRepository = listentryRepository;
        this.animeRepository = animeRepository;
    }

    // Create new listentry from e.g. a request
    public Listentry createListentry(ListentryDTO dto) {
        // Find existing Anime
        Optional<Anime> animeEntity = animeRepository.findById(dto.getAnimeId());

        // Todo: Handle Error if no Anime was found
        if (animeEntity.isEmpty()) {
            throw new RuntimeException("Anime not found with id: " + dto.getAnimeId());
        }

        Anime anime = animeEntity.get();

        Listentry listentry = new Listentry();
        listentry.setAnime(anime);
        listentry.setStatus(dto.getStatus());
        listentry.setProgress(dto.getProgress());
        listentry.setStartdate(dto.getStartdate());
        listentry.setEnddate(dto.getEnddate());
        listentry.setRating(dto.getRating());

        return listentryRepository.save(listentry);
    }
}
