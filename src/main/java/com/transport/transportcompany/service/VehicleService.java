package com.transport.transportcompany.service;

import com.transport.transportcompany.dto.VehicleCreateDto;
import com.transport.transportcompany.dto.VehicleDto;

import java.util.List;

public interface VehicleService {

    List<VehicleDto> getAll();

    VehicleDto getVehicleById(Integer id);

    VehicleDto create(VehicleCreateDto dto);

    void deleteVehicle(Integer id);
}
