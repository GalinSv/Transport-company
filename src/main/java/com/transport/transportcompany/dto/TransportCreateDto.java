package com.transport.transportcompany.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class TransportCreateDto {
    private Integer driverId;
    private Integer clientId;
    private Integer vehicleId;
    private String startPoint;
    private String endPoint;
    private LocalDateTime departureAt;
    private LocalDateTime arrivalAt;
    private String typeOfTransport;
    private Double cargoWeight;
    private Integer passengerCount;
    private Double price;
}
