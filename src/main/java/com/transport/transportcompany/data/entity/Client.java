package com.transport.transportcompany.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClient")
    private Integer idClient;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Company_idCompany", nullable = false)
    private Company company;
}
