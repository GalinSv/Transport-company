package com.transport.transportcompany.dto;

import com.transport.transportcompany.data.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Builder
public class EmployeeDto {
    private Integer idEmployee;
    private String firstName;
    private String lastName;
    private Role role;
    private Double salary;
    private Integer companyId;
    private Set<Integer> qualificationIds;
}

