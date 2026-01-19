package com.transport.transportcompany.data.repo;

import com.transport.transportcompany.data.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransportRepository extends JpaRepository<Transport, Integer> {

    List<Transport> findByClient_IdClient(Integer clientId);

    List<Transport> findByDriver_IdEmployee(Integer driverId);

    List<Transport> findByPaid(Boolean paid);

    List<Transport> findByEndPointIgnoreCase(String endPoint);


    @Query("SELECT COUNT(t) FROM Transport t")
    Long countAllTransports();

    @Query("""
        SELECT COALESCE(SUM(t.price), 0)
        FROM Transport t
        WHERE t.paid = true
    """)
    Double totalRevenue();

    @Query("""
        SELECT t.driver, COUNT(t)
        FROM Transport t
        GROUP BY t.driver
    """)
    List<Object[]> transportCountPerDriver();

    @Query("""
        SELECT t.driver, COALESCE(SUM(t.price), 0)
        FROM Transport t
        WHERE t.paid = true
        GROUP BY t.driver
    """)
    List<Object[]> revenuePerDriver();

    @Query("""
        SELECT COALESCE(SUM(t.price), 0)
        FROM Transport t
        WHERE t.paid = true
          AND t.departureAt BETWEEN :from AND :to
    """)
    Double revenueForPeriod(LocalDateTime from, LocalDateTime to);
}