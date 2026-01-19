package com.transport.transportcompany.service.impl;
import java.util.List;
import java.util.stream.Collectors;
import com.transport.transportcompany.data.entity.*;
import com.transport.transportcompany.data.repo.CompanyRepository;
import com.transport.transportcompany.data.repo.EmployeeRepository;
import com.transport.transportcompany.data.repo.QualificationRepository;
import com.transport.transportcompany.dto.EmployeeCreateDto;
import com.transport.transportcompany.dto.EmployeeDto;
import com.transport.transportcompany.dto.EmployeeUpdateDto;
import com.transport.transportcompany.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final QualificationRepository qualificationRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               CompanyRepository companyRepository,
                               QualificationRepository qualificationRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.qualificationRepository = qualificationRepository;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
    }

    @Override
    public List<EmployeeDto> getEmployeesSortedBySalary() {
        return employeeRepository.findAllByOrderBySalaryDesc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesByQualification(String qualification) {
        return employeeRepository.findByQualifications_TypeOfQualification(qualification)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public EmployeeDto createEmployee(EmployeeCreateDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        Set<Qualification> qualifications = dto.getQualificationIds() == null
                ? Set.of()
                : dto.getQualificationIds().stream()
                .map(id -> qualificationRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Qualification not found: " + id)))
                .collect(Collectors.toSet());

        if (dto.getRole() == Role.DRIVER && qualifications.isEmpty()) {
            throw new IllegalArgumentException("Driver must have at least one qualification");
        }

        Employee saved = employeeRepository.save(
                Employee.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .role(dto.getRole())
                        .salary(dto.getSalary())
                        .company(company)
                        .qualifications(qualifications)
                        .build()
        );

        return toDto(saved);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDto toDto(Employee e) {
        return EmployeeDto.builder()
                .idEmployee(e.getIdEmployee())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .role(e.getRole())
                .salary(e.getSalary())
                .companyId(e.getCompany().getIdCompany())
                .qualificationIds(
                        e.getQualifications().stream()
                                .map(Qualification::getIdQualification)
                                .collect(Collectors.toSet())
                )
                .build();
    }
    @Override
    public EmployeeDto updateEmployee(Integer id, EmployeeUpdateDto dto) {
        Employee e = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));

        if (dto.getRole() != null) {
            e.setRole(dto.getRole());
        }

        if (dto.getSalary() != null) {
            e.setSalary(dto.getSalary());
        }

        if (dto.getQualificationIds() != null) {
            Set<Qualification> qualifications = dto.getQualificationIds().stream()
                    .map(qId -> qualificationRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Qualification not found: " + qId)))
                    .collect(Collectors.toSet());
            e.setQualifications(qualifications);
        }

        return toDto(e);
    }

}