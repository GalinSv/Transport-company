package com.transport.transportcompany.service;

import com.transport.transportcompany.data.entity.*;
import com.transport.transportcompany.data.repo.*;
import com.transport.transportcompany.dto.TransportCreateDto;
import com.transport.transportcompany.dto.TransportDto;
import com.transport.transportcompany.dto.TransportUpdateDto;
import com.transport.transportcompany.service.impl.TransportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransportServiceImplTest {

    @Mock
    private TransportRepository transportRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private TransportServiceImpl transportService;

    private Employee driver;
    private Client client;
    private Vehicle vehicle;
    private Transport transport;

    @BeforeEach
    void setUp() {
        driver = Employee.builder()
                .idEmployee(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        client = Client.builder()
                .idClient(2)
                .name("ACME Corp")
                .build();

        vehicle = Vehicle.builder()
                .idVehicle(3)
                .plateNumber("CA1234AB")
                .build();

        transport = Transport.builder()
                .idTransport(10)
                .driver(driver)
                .client(client)
                .vehicle(vehicle)
                .startPoint("Sofia")
                .endPoint("Plovdiv")
                .departureAt(LocalDateTime.now())
                .arrivalAt(LocalDateTime.now().plusHours(2))
                .typeOfTransport(TransportType.CARGO)
                .cargoWeight(1200.0)
                .passengerCount(null)
                .price(500.0)
                .status(TransportStatus.PLANNED)
                .paid(false)
                .build();
    }


    @Test
    void getByDestination_returnsMappedDtos() {
        when(transportRepository.findByEndPointIgnoreCase("Plovdiv"))
                .thenReturn(List.of(transport));

        List<TransportDto> result =
                transportService.getByDestination("Plovdiv");

        assertEquals(1, result.size());
        assertEquals("Plovdiv", result.get(0).getEndPoint());

        verify(transportRepository).findByEndPointIgnoreCase("Plovdiv");
    }

    @Test
    void getAll_returnsAllTransportsAsDto() {
        when(transportRepository.findAll())
                .thenReturn(List.of(transport));

        List<TransportDto> result = transportService.getAll();

        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getIdTransport());
    }



    @Test
    void getById_existingId_returnsDto() {
        when(transportRepository.findById(10))
                .thenReturn(Optional.of(transport));

        TransportDto result = transportService.getById(10);

        assertEquals("Sofia", result.getStartPoint());
        assertEquals("Plovdiv", result.getEndPoint());
    }

    @Test
    void getById_missingId_throwsException() {
        when(transportRepository.findById(99))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> transportService.getById(99)
        );

        assertEquals("Transport not found: 99", ex.getMessage());
    }

    @Test
    void create_validInput_createsTransport() {
        TransportCreateDto dto = TransportCreateDto.builder()
                .driverId(1)
                .clientId(2)
                .vehicleId(3)
                .startPoint("Sofia")
                .endPoint("Varna")
                .departureAt(LocalDateTime.now())
                .arrivalAt(LocalDateTime.now().plusHours(6))
                .typeOfTransport("CARGO")
                .cargoWeight(2000.0)
                .passengerCount(null)
                .price(900.0)
                .build();

        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(driver));
        when(clientRepository.findById(2))
                .thenReturn(Optional.of(client));
        when(vehicleRepository.findById(3))
                .thenReturn(Optional.of(vehicle));

        when(transportRepository.save(any(Transport.class)))
                .thenAnswer(invocation -> {
                    Transport saved = invocation.getArgument(0);
                    saved.setIdTransport(99);
                    return saved;
                });

        TransportDto result = transportService.create(dto);

        assertEquals(99, result.getIdTransport());
        assertEquals("Varna", result.getEndPoint());
        assertEquals("PLANNED", result.getStatus());
        assertFalse(result.getPaid());

        verify(transportRepository).save(any(Transport.class));
    }

    @Test
    void create_missingDriver_throwsException() {
        TransportCreateDto dto = TransportCreateDto.builder()
                .driverId(1)
                .clientId(2)
                .vehicleId(3)
                .build();

        when(employeeRepository.findById(1))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> transportService.create(dto)
        );

        assertEquals("Driver not found", ex.getMessage());
    }


    @Test
    void update_partialUpdate_updatesOnlyProvidedFields() {
        when(transportRepository.findById(10))
                .thenReturn(Optional.of(transport));

        TransportUpdateDto dto = TransportUpdateDto.builder()
                .price(700.0)
                .paid(true)
                .build();

        TransportDto result = transportService.update(10, dto);

        assertEquals(700.0, result.getPrice());
        assertTrue(result.getPaid());
        assertNotNull(result.getPaidAt());
    }


    @Test
    void markAsPaid_setsPaidAndPaidAt() {
        when(transportRepository.findById(10))
                .thenReturn(Optional.of(transport));

        transportService.markAsPaid(10);

        assertTrue(transport.getPaid());
        assertNotNull(transport.getPaidAt());
    }

    @Test
    void markAsPaid_missingTransport_throwsException() {
        when(transportRepository.findById(55))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> transportService.markAsPaid(55)
        );

        assertEquals("Transport not found", ex.getMessage());
    }

    @Test
    void delete_callsRepositoryDeleteById() {
        transportService.delete(10);

        verify(transportRepository).deleteById(10);
    }
}
