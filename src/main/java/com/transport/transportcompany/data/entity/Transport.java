package com.transport.transportcompany.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transport")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransport")
    private Integer idTransport;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Employee driver;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idClient", nullable = false)
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idVehicle", nullable = false)
    private Vehicle vehicle;

    @Column(name = "start_point", nullable = false, length = 45)
    private String startPoint;

    @Column(name = "end_point", nullable = false, length = 45)
    private String endPoint;

    @Column(name = "departureAt", nullable = false)
    private LocalDateTime departureAt;

    @Column(name = "arrivalAt", nullable = false)
    private LocalDateTime arrivalAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeOfTransport", nullable = false, length = 20)
    private TransportType typeOfTransport;

    @Column(name = "cargoWeight")
    private Double cargoWeight;

    @Column(name = "passengerCount")
    private Integer passengerCount;

    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TransportStatus status;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
