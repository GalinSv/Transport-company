package com.transport.transportcompany.service.impl;

import com.transport.transportcompany.data.entity.*;
import com.transport.transportcompany.data.repo.*;
import com.transport.transportcompany.dto.TransportCreateDto;
import com.transport.transportcompany.dto.TransportDto;
import com.transport.transportcompany.dto.TransportUpdateDto;
import com.transport.transportcompany.service.TransportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;

    public TransportServiceImpl(TransportRepository transportRepository,
                                EmployeeRepository employeeRepository,
                                ClientRepository clientRepository,
                                VehicleRepository vehicleRepository) {
        this.transportRepository = transportRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<TransportDto> getByDestination(String destination) {
        return transportRepository.findByEndPointIgnoreCase(destination)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<TransportDto> getAll() {
        return transportRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public TransportDto update(Integer id, TransportUpdateDto dto) {
        Transport t = transportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transport not found: " + id));

        if (dto.getPrice() != null) {
            t.setPrice(dto.getPrice());
        }

        if (dto.getStatus() != null) {
            t.setStatus(dto.getStatus());
        }

        if (dto.getPaid() != null) {
            t.setPaid(dto.getPaid());
            if (dto.getPaid()) {
                t.setPaidAt(LocalDateTime.now());
            }
        }

        return toDto(t);
    }

    @Override
    public TransportDto getById(Integer id) {
        Transport t = transportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transport not found: " + id));
        return toDto(t);
    }

    @Override
    public TransportDto create(TransportCreateDto dto) {
        Employee driver = employeeRepository.findById(dto.getDriverId())
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        Transport saved = transportRepository.save(
                Transport.builder()
                        .driver(driver)
                        .client(client)
                        .vehicle(vehicle)
                        .startPoint(dto.getStartPoint())
                        .endPoint(dto.getEndPoint())
                        .departureAt(dto.getDepartureAt())
                        .arrivalAt(dto.getArrivalAt())
                        .typeOfTransport(TransportType.valueOf(dto.getTypeOfTransport()))
                        .cargoWeight(dto.getCargoWeight())
                        .passengerCount(dto.getPassengerCount())
                        .price(dto.getPrice())
                        .status(TransportStatus.PLANNED)
                        .paid(false)
                        .build()
        );

        return toDto(saved);
    }

    @Override
    public void markAsPaid(Integer id) {
        Transport t = transportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transport not found"));

        t.setPaid(true);
        t.setPaidAt(LocalDateTime.now());
    }

    @Override
    public void delete(Integer id) {
        transportRepository.deleteById(id);
    }

    private TransportDto toDto(Transport t) {
        return TransportDto.builder()
                .idTransport(t.getIdTransport())
                .driverId(t.getDriver().getIdEmployee())
                .clientId(t.getClient().getIdClient())
                .vehicleId(t.getVehicle().getIdVehicle())
                .startPoint(t.getStartPoint())
                .endPoint(t.getEndPoint())
                .departureAt(t.getDepartureAt())
                .arrivalAt(t.getArrivalAt())
                .typeOfTransport(t.getTypeOfTransport().name())
                .cargoWeight(t.getCargoWeight())
                .passengerCount(t.getPassengerCount())
                .price(t.getPrice())
                .status(t.getStatus().name())
                .paid(t.getPaid())
                .paidAt(t.getPaidAt())
                .build();
    }
}
