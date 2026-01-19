package com.transport.transportcompany.data.repo;

import com.transport.transportcompany.data.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByCompany_IdCompany(Integer companyId);
}
