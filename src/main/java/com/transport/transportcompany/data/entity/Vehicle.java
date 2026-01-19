package com.transport.transportcompany.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVehicle")
    private Integer idVehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 45)
    private VehicleType type;

    @Column(name = "plate_number", nullable = false, length = 45, unique = true)
    private String plateNumber;

    @Column(name = "brand", length = 45)
    private String brand;

    @Column(name = "model", length = 45)
    private String model;

    @Column(name = "capacity_kg")
    private Double capacityKg;

    @Column(name = "capacity_seats")
    private Integer capacitySeats;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Company_idCompany", nullable = false)
    private Company company;
}
