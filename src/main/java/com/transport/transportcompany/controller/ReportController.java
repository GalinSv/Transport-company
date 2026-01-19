package com.transport.transportcompany.controller;

import com.transport.transportcompany.dto.DriverRevenueDto;
import com.transport.transportcompany.dto.DriverTransportCountDto;
import com.transport.transportcompany.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/transports/count")
    public Long totalTransports() {
        return reportService.getTotalTransportCount();
    }

    @GetMapping("/revenue/total")
    public Double totalRevenue() {
        return reportService.getTotalRevenue();
    }

    @GetMapping("/drivers/count")
    public List<DriverTransportCountDto> transportsPerDriver() {
        return reportService.getTransportCountPerDriver();
    }

    @GetMapping("/drivers/revenue")
    public List<DriverRevenueDto> revenuePerDriver() {
        return reportService.getRevenuePerDriver();
    }
    @GetMapping("/revenue/period")
    public Double revenueForPeriod(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return reportService.getRevenueForPeriod(from, to);
    }
}