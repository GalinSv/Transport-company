package com.transport.transportcompany.data.repo;

import com.transport.transportcompany.data.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QualificationRepository extends JpaRepository<Qualification, Integer> {

    Optional<Qualification> findByTypeOfQualification(String typeOfQualification);
}
