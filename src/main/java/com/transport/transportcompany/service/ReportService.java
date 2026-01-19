package com.transport.transportcompany.service;

import com.transport.transportcompany.dto.DriverRevenueDto;
import com.transport.transportcompany.dto.DriverTransportCountDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    Long getTotalTransportCount();

    Double getTotalRevenue();

    List<DriverTransportCountDto> getTransportCountPerDriver();

    List<DriverRevenueDto> getRevenuePerDriver();

    Double getRevenueForPeriod(LocalDateTime from, LocalDateTime to);
}
