package com.transport.transportcompany.controller;

import com.transport.transportcompany.dto.TransportCreateDto;
import com.transport.transportcompany.dto.TransportDto;

import com.transport.transportcompany.dto.TransportUpdateDto;
import com.transport.transportcompany.service.TransportService;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/transports")
public class TransportController {

    private final TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping
    public List<TransportDto> getAll() {
        return transportService.getAll();
    }

    @GetMapping("/{id}")
    public TransportDto getById(@PathVariable Integer id) {
        return transportService.getById(id);
    }

    @PostMapping
    public TransportDto create(@RequestBody TransportCreateDto dto) {
        return transportService.create(dto);
    }

    @PutMapping("/{id}/pay")
    public void markAsPaid(@PathVariable Integer id) {
        transportService.markAsPaid(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        transportService.delete(id);
    }

    @PutMapping("/{id}")
    public TransportDto update(
            @PathVariable Integer id,
            @RequestBody TransportUpdateDto dto
    ) {
        return transportService.update(id, dto);
    }

    @GetMapping("/by-destination")
    public List<TransportDto> getByDestination(
            @RequestParam String destination
    ) {
        return transportService.getByDestination(destination);
    }
}
