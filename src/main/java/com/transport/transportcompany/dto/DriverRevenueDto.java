package com.transport.transportcompany.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DriverRevenueDto {
    private Integer driverId;
    private String driverFirstName;
    private String driverLastName;
    private Double revenue;
}
