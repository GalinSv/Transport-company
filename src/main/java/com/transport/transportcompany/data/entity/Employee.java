package com.transport.transportcompany.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmployee")
    private Integer idEmployee;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Company_idCompany", nullable = false)
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "employee_has_qualification",
            joinColumns = @JoinColumn(name = "Employee_idEmployee"),
            inverseJoinColumns = @JoinColumn(name = "Qualification_idQualification")
    )
    private Set<Qualification> qualifications;
}
