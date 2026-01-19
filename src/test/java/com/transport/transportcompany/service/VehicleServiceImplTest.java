package com.transport.transportcompany.service;
import com.transport.transportcompany.data.entity.Company;
import com.transport.transportcompany.data.entity.Vehicle;
import com.transport.transportcompany.data.entity.VehicleType;
import com.transport.transportcompany.data.repo.CompanyRepository;
import com.transport.transportcompany.data.repo.VehicleRepository;
import com.transport.transportcompany.dto.VehicleCreateDto;
import com.transport.transportcompany.dto.VehicleDto;
import com.transport.transportcompany.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Company company;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        company = Company.builder()
                .idCompany(1)
                .name("Transport Corp")
                .build();

        vehicle = Vehicle.builder()
                .idVehicle(10)
                .type(VehicleType.valueOf("TRUCK"))
                .plateNumber("CA1234AB")
                .brand("Volvo")
                .model("FH")
                .capacityKg(18000.0)
                .capacitySeats(null)
                .company(company)
                .build();
    }

    @Test
    void getAll_returnsAllVehiclesMappedToDto() {
        when(vehicleRepository.findAll())
                .thenReturn(List.of(vehicle));

        List<VehicleDto> result = vehicleService.getAll();

        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getIdVehicle());
        assertEquals("CA1234AB", result.get(0).getPlateNumber());

        verify(vehicleRepository).findAll();
    }

    @Test
    void getVehicleById_existingId_returnsDto() {
        when(vehicleRepository.findById(10))
                .thenReturn(Optional.of(vehicle));

        VehicleDto result = vehicleService.getVehicleById(10);

        assertEquals("Volvo", result.getBrand());
        assertEquals("FH", result.getModel());
        assertEquals(1, result.getCompanyId());
    }

    @Test
    void getVehicleById_missingId_throwsException() {
        when(vehicleRepository.findById(99))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.getVehicleById(99)
        );

        assertEquals("Vehicle not found: 99", ex.getMessage());
    }

    @Test
    void create_validInput_createsVehicle() {
        VehicleCreateDto dto = VehicleCreateDto.builder()
                .type(VehicleType.valueOf("BUS"))
                .plateNumber("CB5678CD")
                .brand("Mercedes")
                .model("Tourismo")
                .capacityKg(null)
                .capacitySeats(55)
                .companyId(1)
                .build();

        when(companyRepository.findById(1))
                .thenReturn(Optional.of(company));

        when(vehicleRepository.save(any(Vehicle.class)))
                .thenAnswer(invocation -> {
                    Vehicle saved = invocation.getArgument(0);
                    saved.setIdVehicle(20);
                    return saved;
                });

        VehicleDto result = vehicleService.create(dto);

        assertEquals(20, result.getIdVehicle());
        assertEquals(VehicleType.valueOf("BUS"), result.getType());
        assertEquals("Mercedes", result.getBrand());
        assertEquals(55, result.getCapacitySeats());
        assertEquals(1, result.getCompanyId());

        verify(vehicleRepository).save(any(Vehicle.class));
    }

    @Test
    void create_missingCompany_throwsException() {
        VehicleCreateDto dto = VehicleCreateDto.builder()
                .companyId(1)
                .build();

        when(companyRepository.findById(1))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.create(dto)
        );

        assertEquals("Company not found", ex.getMessage());
        verify(vehicleRepository, never()).save(any());
    }


    @Test
    void deleteVehicle_existingVehicle_deletes() {
        when(vehicleRepository.findById(10))
                .thenReturn(Optional.of(vehicle));

        vehicleService.deleteVehicle(10);

        verify(vehicleRepository).delete(vehicle);
    }

    @Test
    void deleteVehicle_missingVehicle_throwsException() {
        when(vehicleRepository.findById(50))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.deleteVehicle(50)
        );

        assertEquals("Vehicle not found: 50", ex.getMessage());
        verify(vehicleRepository, never()).delete(any());
    }
}
