package com.transport.transportcompany.service;

import com.transport.transportcompany.data.entity.*;
import com.transport.transportcompany.data.repo.CompanyRepository;
import com.transport.transportcompany.data.repo.EmployeeRepository;
import com.transport.transportcompany.data.repo.QualificationRepository;
import com.transport.transportcompany.dto.EmployeeCreateDto;
import com.transport.transportcompany.dto.EmployeeDto;
import com.transport.transportcompany.dto.EmployeeUpdateDto;
import com.transport.transportcompany.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private QualificationRepository qualificationRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Company company;
    private Employee employee;
    private Qualification qualification;

    @BeforeEach
    void setUp() {
        company = Company.builder()
                .idCompany(1)
                .build();

        qualification = Qualification.builder()
                .idQualification(10)
                .build();

        employee = Employee.builder()
                .idEmployee(1)
                .firstName("John")
                .lastName("Doe")
                .role(Role.OFFICE)
                .salary(4000.0)
                .company(company)
                .qualifications(Set.of())
                .build();
    }

    @Test
    void getAllEmployees_returnsDtos() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getIdEmployee());
    }

    @Test
    void getEmployeeById_notFound_throwsException() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.getEmployeeById(1)
        );

        assertEquals("Employee not found: 1", ex.getMessage());
    }

    @Test
    void createEmployee_driverWithoutQualification_throwsException() {
        EmployeeCreateDto dto = EmployeeCreateDto.builder()
                .firstName("Driver")
                .lastName("One")
                .role(Role.DRIVER)
                .salary(3000.0)
                .companyId(1)
                .qualificationIds(Set.of())
                .build();

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.createEmployee(dto)
        );

        assertEquals("Driver must have at least one qualification", ex.getMessage());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void updateEmployee_salaryOnly_updatesSalary() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        EmployeeUpdateDto dto = new EmployeeUpdateDto();
        dto.setSalary(5500.0);

        EmployeeDto result = employeeService.updateEmployee(1, dto);

        assertEquals(5500.0, result.getSalary());
        assertEquals(Role.OFFICE, result.getRole());
    }

    @Test
    void deleteEmployee_callsRepository() {
        employeeService.deleteEmployee(1);

        verify(employeeRepository).deleteById(1);
    }
}
