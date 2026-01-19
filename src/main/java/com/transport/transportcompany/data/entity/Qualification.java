package com.transport.transportcompany.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "qualification")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idQualification")
    private Integer idQualification;

    @Column(name = "typeOfQualification", nullable = false, unique = true, length = 50)
    private String typeOfQualification;
}
