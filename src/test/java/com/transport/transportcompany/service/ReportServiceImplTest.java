package com.transport.transportcompany.service;
import com.transport.transportcompany.data.entity.Employee;
import com.transport.transportcompany.data.repo.TransportRepository;
import com.transport.transportcompany.dto.DriverRevenueDto;
import com.transport.transportcompany.dto.DriverTransportCountDto;
import com.transport.transportcompany.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private TransportRepository transportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    private Employee driverA;
    private Employee driverB;

    @BeforeEach
    void setUp() {
        driverA = Employee.builder()
                .idEmployee(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        driverB = Employee.builder()
                .idEmployee(2)
                .firstName("Jane")
                .lastName("Smith")
                .build();
    }


    @Test
    void getTotalTransportCount_returnsRepositoryValue() {
        when(transportRepository.countAllTransports())
                .thenReturn(42L);

        Long result = reportService.getTotalTransportCount();

        assertEquals(42L, result);
        verify(transportRepository).countAllTransports();
    }


    @Test
    void getTotalRevenue_returnsRepositoryValue() {
        when(transportRepository.totalRevenue())
                .thenReturn(12500.75);

        Double result = reportService.getTotalRevenue();

        assertEquals(12500.75, result);
        verify(transportRepository).totalRevenue();
    }


    @Test
    void getTransportCountPerDriver_mapsRawResultsCorrectly() {
        Object[] row1 = { driverA, 5L };
        Object[] row2 = { driverB, 8L };

        when(transportRepository.transportCountPerDriver())
                .thenReturn(List.of(row1, row2));

        List<DriverTransportCountDto> result =
                reportService.getTransportCountPerDriver();

        assertEquals(2, result.size());

        DriverTransportCountDto first = result.get(0);
        assertEquals(1, first.getDriverId());
        assertEquals("John", first.getDriverFirstName());
        assertEquals("Doe", first.getDriverLastName());
        assertEquals(5L, first.getTransportCount());

        verify(transportRepository).transportCountPerDriver();
    }

    @Test
    void getRevenuePerDriver_mapsRawResultsCorrectly() {
        Object[] row1 = { driverA, 3200.0 };
        Object[] row2 = { driverB, 4700.5 };

        when(transportRepository.revenuePerDriver())
                .thenReturn(List.of(row1, row2));

        List<DriverRevenueDto> result =
                reportService.getRevenuePerDriver();

        assertEquals(2, result.size());

        DriverRevenueDto first = result.get(0);
        assertEquals(1, first.getDriverId());
        assertEquals("John", first.getDriverFirstName());
        assertEquals("Doe", first.getDriverLastName());
        assertEquals(3200.0, first.getRevenue());

        verify(transportRepository).revenuePerDriver();
    }

    @Test
    void getRevenueForPeriod_returnsRepositoryValue() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to   = LocalDateTime.of(2024, 12, 31, 23, 59);

        when(transportRepository.revenueForPeriod(from, to))
                .thenReturn(8900.0);

        Double result = reportService.getRevenueForPeriod(from, to);

        assertEquals(8900.0, result);
        verify(transportRepository).revenueForPeriod(from, to);
    }
}
