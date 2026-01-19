package com.transport.transportcompany.dto;

import com.transport.transportcompany.data.entity.VehicleType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleDto {

    private Integer idVehicle;
    private VehicleType type;
    private String plateNumber;
    private String brand;
    private String model;
    private Double capacityKg;
    private Integer capacitySeats;
    private Integer companyId;
}
