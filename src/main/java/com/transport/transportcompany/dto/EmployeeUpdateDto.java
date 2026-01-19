package com.transport.transportcompany.dto;

import com.transport.transportcompany.data.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter @Setter
public class EmployeeUpdateDto {
    private Double salary;
    private Role role;
    private Set<Integer> qualificationIds;
}