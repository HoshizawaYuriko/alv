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

    @PostMapping
    public ResponseEntity<Listentry> createListentry(@RequestBody ListentryDTO dto) {
        Listentry listentry = listentryService.createListentry(dto);
        return ResponseEntity.ok(listentry);
    }
}
