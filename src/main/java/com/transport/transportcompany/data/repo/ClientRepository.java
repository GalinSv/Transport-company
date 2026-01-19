package com.transport.transportcompany.data.repo;

import com.transport.transportcompany.data.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByCompany_IdCompany(Integer companyId);
}
