package com.transport.transportcompany.data.repo;

import com.transport.transportcompany.data.entity.Employee;
import com.transport.transportcompany.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByCompany_IdCompany(Integer companyId);

    List<Employee> findByRole(Role role);

    List<Employee> findAllByOrderBySalaryDesc();

    List<Employee> findByQualifications_TypeOfQualification(String type);
}
