package com.example.alv.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.alv.dto.ListentryDTO;
import com.example.alv.model.Listentry;
import com.example.alv.service.ListentryService;

@RestController
@RequestMapping("/listentry")
public class ListentryController {
    private final ListentryService listentryService;

    public ListentryController(ListentryService listentryService) {
        this.listentryService = listentryService;
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

    // Delete an existing listentry by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListentry(@PathVariable Long id) {
        listentryService.deleteListentry(id);
        return ResponseEntity.noContent().build();
    }
}
