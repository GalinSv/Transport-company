package com.transport.transportcompany.controller;

import com.transport.transportcompany.dto.VehicleCreateDto;
import com.transport.transportcompany.dto.VehicleDto;
import com.transport.transportcompany.service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<VehicleDto> getAll() {
        return vehicleService.getAll();
    }

    @GetMapping("/{id}")
    public VehicleDto getById(@PathVariable Integer id) {
        return vehicleService.getVehicleById(id);
    }

    @PostMapping
    public VehicleDto create(@RequestBody VehicleCreateDto dto) {
        return vehicleService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        vehicleService.deleteVehicle(id);
    }
}
