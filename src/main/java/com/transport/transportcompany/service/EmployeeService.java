package com.transport.transportcompany.service;

import com.transport.transportcompany.dto.EmployeeCreateDto;
import com.transport.transportcompany.dto.EmployeeDto;
import com.transport.transportcompany.dto.EmployeeUpdateDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Integer id);

    EmployeeDto createEmployee(EmployeeCreateDto dto);
    List<EmployeeDto> getEmployeesSortedBySalary();

    List<EmployeeDto> getEmployeesByQualification(String qualification);
    EmployeeDto updateEmployee(Integer id, EmployeeUpdateDto dto);

    void deleteEmployee(Integer id);

}
