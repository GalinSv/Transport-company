package com.transport.transportcompany.controller;

import com.transport.transportcompany.dto.EmployeeCreateDto;
import com.transport.transportcompany.dto.EmployeeDto;
import com.transport.transportcompany.dto.EmployeeUpdateDto;
import com.transport.transportcompany.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeCreateDto dto) {
        return employeeService.createEmployee(dto);
    }

    @PutMapping("/{id}")
    public EmployeeDto update(
            @PathVariable Integer id,
            @RequestBody EmployeeUpdateDto dto
    ) {
        return employeeService.updateEmployee(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/sorted-by-salary")
    public List<EmployeeDto> getEmployeesSortedBySalary() {
        return employeeService.getEmployeesSortedBySalary();
    }

    @GetMapping("/by-qualification")
    public List<EmployeeDto> getEmployeesByQualification(
            @RequestParam String qualification
    ) {
        return employeeService.getEmployeesByQualification(qualification);
    }
}
