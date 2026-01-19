package com.transport.transportcompany.data.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Company")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompany")
    private Integer idCompany;
    @Column(name = "name", nullable = false)
    private String name;

}
