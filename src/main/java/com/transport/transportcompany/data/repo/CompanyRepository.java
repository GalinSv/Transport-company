package com.transport.transportcompany.data.repo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.transport.transportcompany.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByName(String name);

    List<Company> findAllByOrderByNameAsc();

    @Query("""
        SELECT c, COALESCE(SUM(t.price), 0)
        FROM Company c
        LEFT JOIN Transport t ON t.client.company = c AND t.paid = true
        GROUP BY c
        ORDER BY COALESCE(SUM(t.price), 0) DESC
    """)
    List<Object[]> findCompaniesWithRevenue();
}
