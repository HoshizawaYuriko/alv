package com.example.alv.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.alv.api.dto.ListentryDTO;
import com.example.alv.application.ListentryService;
import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.listentry.Status;

@RestController
@RequestMapping("/listentry")
public class ListentryController {
    private final ListentryService listentryService;

    public ListentryController(ListentryService listentryService) {
        this.listentryService = listentryService;
    }

    // Get all listentries
    @GetMapping("/list")
    public ResponseEntity<List<Listentry>> getAllListentries() {
        return ResponseEntity.ok(listentryService.getAllListentries());
    }

    // Get listentry by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<Listentry> getListentryById(@PathVariable Long id) {
        Listentry listentry = listentryService.getListentryById(id);
        return ResponseEntity.ok(listentry);
    }

    // Get listentries filtered by status
    @GetMapping("/filter/status")
    public ResponseEntity<List<Listentry>> getByStatus(@RequestParam Status status) {
        return ResponseEntity.ok(listentryService.findByStatus(status));
    }

    // Get listentries filtered by Genre of the Anime
    @GetMapping("/filter/genre")
    public ResponseEntity<List<Listentry>> getByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(listentryService.findByGenre(genre));
    }

    // Get listentries filtered by rating > given rating
    @GetMapping("/filter/rating")
    public ResponseEntity<List<Listentry>> getByRating(@RequestParam int min) {
        return ResponseEntity.ok(listentryService.findByRatingGreaterThan(min));
    }

    // Create a new listentry from given form data
    @PostMapping("/create")
    public ResponseEntity<Listentry> createListentry(@RequestBody ListentryDTO dto) {
        Listentry newListentry = listentryService.createListentry(dto);
        return ResponseEntity.ok(newListentry);
    }

    // Increase progress of given Listentry by 1
    @PutMapping("/progress/increment/{id}")
    public ResponseEntity<Listentry> incrementProgress(@PathVariable Long id) {
        Listentry updatedListentry = listentryService.incrementProgress(id);
        return ResponseEntity.ok(updatedListentry);
    }

    // Set progress of given Listentry with the given amount
    @PutMapping("/progress/set/{id}")
    public ResponseEntity<Listentry> setProgress(@PathVariable Long id, @RequestParam int episode) {
        Listentry updatedListentry = listentryService.setProgress(id, episode);
        return ResponseEntity.ok(updatedListentry);
    }

    // Set status of given Listentry
    @PutMapping("/status/{id}")
    public ResponseEntity<Listentry> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        Listentry updatedListentry = listentryService.updateStatus(id, status);
        return ResponseEntity.ok(updatedListentry);
    }

    // Delete an existing listentry by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListentry(@PathVariable Long id) {
        listentryService.deleteListentry(id);
        return ResponseEntity.noContent().build();
    }
}
