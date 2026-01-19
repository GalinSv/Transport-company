package com.transport.transportcompany.dto;

import com.transport.transportcompany.data.entity.VehicleType;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class VehicleCreateDto {
    private VehicleType type;
    private String plateNumber;
    private String brand;
    private String model;
    private Integer capacitySeats;
    private Double capacityKg;
    private Integer companyId;
}
