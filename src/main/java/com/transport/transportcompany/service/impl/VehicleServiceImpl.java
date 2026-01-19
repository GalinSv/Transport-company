package com.transport.transportcompany.service.impl;

import com.transport.transportcompany.data.entity.Company;
import com.transport.transportcompany.data.entity.Vehicle;
import com.transport.transportcompany.data.repo.CompanyRepository;
import com.transport.transportcompany.data.repo.VehicleRepository;
import com.transport.transportcompany.dto.VehicleCreateDto;
import com.transport.transportcompany.dto.VehicleDto;
import com.transport.transportcompany.service.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CompanyRepository companyRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository,
                              CompanyRepository companyRepository) {
        this.vehicleRepository = vehicleRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<VehicleDto> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public VehicleDto getVehicleById(Integer id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found: " + id));
        return toDto(vehicle);
    }

    @Override
    public VehicleDto create(VehicleCreateDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        Vehicle vehicle = Vehicle.builder()
                .type(dto.getType())
                .plateNumber(dto.getPlateNumber())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .capacityKg(dto.getCapacityKg())
                .capacitySeats(dto.getCapacitySeats())
                .company(company)
                .build();

        return toDto(vehicleRepository.save(vehicle));
    }


    @Override
    public void deleteVehicle(Integer id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found: " + id));
        vehicleRepository.delete(vehicle);
    }

    private VehicleDto toDto(Vehicle v) {
        return VehicleDto.builder()
                .idVehicle(v.getIdVehicle())
                .type(v.getType())
                .plateNumber(v.getPlateNumber())
                .brand(v.getBrand())
                .model(v.getModel())
                .capacityKg(v.getCapacityKg())
                .capacitySeats(v.getCapacitySeats())
                .companyId(v.getCompany().getIdCompany())
                .build();
    }
}
