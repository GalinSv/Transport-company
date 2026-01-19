package com.transport.transportcompany.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TransportDto {
    private Integer idTransport;
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
    private String status;
    private Boolean paid;
    private LocalDateTime paidAt;
}
