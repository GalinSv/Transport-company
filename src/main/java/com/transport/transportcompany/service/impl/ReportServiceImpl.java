package com.transport.transportcompany.service.impl;

import com.transport.transportcompany.data.entity.Employee;
import com.transport.transportcompany.data.repo.TransportRepository;
import com.transport.transportcompany.dto.DriverRevenueDto;
import com.transport.transportcompany.dto.DriverTransportCountDto;
import com.transport.transportcompany.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final TransportRepository transportRepository;

    public ReportServiceImpl(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @Override
    public Long getTotalTransportCount() {
        return transportRepository.countAllTransports();
    }

    @Override
    public Double getTotalRevenue() {
        return transportRepository.totalRevenue();
    }

    @Override
    public List<DriverTransportCountDto> getTransportCountPerDriver() {
        return transportRepository.transportCountPerDriver()
                .stream()
                .map(row -> {
                    Employee driver = (Employee) row[0];
                    Long count = (Long) row[1];

                    return DriverTransportCountDto.builder()
                            .driverId(driver.getIdEmployee())
                            .driverFirstName(driver.getFirstName())
                            .driverLastName(driver.getLastName())
                            .transportCount(count)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverRevenueDto> getRevenuePerDriver() {
        return transportRepository.revenuePerDriver()
                .stream()
                .map(row -> {
                    Employee driver = (Employee) row[0];
                    Double revenue = (Double) row[1];

                    return DriverRevenueDto.builder()
                            .driverId(driver.getIdEmployee())
                            .driverFirstName(driver.getFirstName())
                            .driverLastName(driver.getLastName())
                            .revenue(revenue)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Double getRevenueForPeriod(LocalDateTime from, LocalDateTime to) {
        return transportRepository.revenueForPeriod(from, to);
    }
}
