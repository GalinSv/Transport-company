package com.transport.transportcompany.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DriverTransportCountDto {
    private Integer driverId;
    private String driverFirstName;
    private String driverLastName;
    private Long transportCount;
}
