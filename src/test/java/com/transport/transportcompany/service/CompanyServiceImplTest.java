package com.transport.transportcompany.service;
import com.transport.transportcompany.data.entity.Company;
import com.transport.transportcompany.data.repo.CompanyRepository;
import com.transport.transportcompany.dto.CompanyCreateDto;
import com.transport.transportcompany.dto.CompanyDto;
import com.transport.transportcompany.dto.CompanyRevenueDto;
import com.transport.transportcompany.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company companyA;
    private Company companyB;

    @BeforeEach
    void setUp() {
        companyA = Company.builder()
                .idCompany(1)
                .name("Alpha Logistics")
                .build();

        companyB = Company.builder()
                .idCompany(2)
                .name("Beta Transport")
                .build();
    }

    @Test
    void getAllSortedByName_returnsCompaniesMappedToDto() {
        when(companyRepository.findAllByOrderByNameAsc())
                .thenReturn(List.of(companyA, companyB));

        List<CompanyDto> result = companyService.getAllSortedByName();

        assertEquals(2, result.size());
        assertEquals("Alpha Logistics", result.get(0).getName());
        assertEquals("Beta Transport", result.get(1).getName());

        verify(companyRepository).findAllByOrderByNameAsc();
    }


    @Test
    void getCompaniesByRevenue_mapsRawQueryResultCorrectly() {
        Object[] row1 = { companyA, 15000.0 };
        Object[] row2 = { companyB, 22000.5 };

        when(companyRepository.findCompaniesWithRevenue())
                .thenReturn(List.of(row1, row2));

        List<CompanyRevenueDto> result = companyService.getCompaniesByRevenue();

        assertEquals(2, result.size());

        CompanyRevenueDto first = result.get(0);
        assertEquals(1, first.getCompanyId());
        assertEquals("Alpha Logistics", first.getName());
        assertEquals(15000.0, first.getRevenue());

        verify(companyRepository).findCompaniesWithRevenue();
    }

    @Test
    void getAll_returnsAllCompaniesAsDto() {
        when(companyRepository.findAll())
                .thenReturn(List.of(companyA));

        List<CompanyDto> result = companyService.getAll();

        assertEquals(1, result.size());
        assertEquals("Alpha Logistics", result.get(0).getName());

        verify(companyRepository).findAll();
    }


    @Test
    void getById_existingId_returnsCompanyDto() {
        when(companyRepository.findById(1))
                .thenReturn(Optional.of(companyA));

        CompanyDto result = companyService.getById(1);

        assertEquals(1, result.getIdCompany());
        assertEquals("Alpha Logistics", result.getName());
    }

    @Test
    void getById_missingId_throwsException() {
        when(companyRepository.findById(99))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.getById(99)
        );

        assertEquals("Company not found: 99", ex.getMessage());
    }


    @Test
    void create_newCompany_savesAndReturnsDto() {
        CompanyCreateDto dto = CompanyCreateDto.builder()
                .name("  New Company  ")
                .build();

        when(companyRepository.existsByName("New Company"))
                .thenReturn(false);

        when(companyRepository.save(any(Company.class)))
                .thenAnswer(invocation -> {
                    Company saved = invocation.getArgument(0);
                    saved.setIdCompany(10);
                    return saved;
                });

        CompanyDto result = companyService.create(dto);

        assertEquals(10, result.getIdCompany());
        assertEquals("New Company", result.getName());

        verify(companyRepository).existsByName("New Company");
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    void create_duplicateName_throwsException() {
        CompanyCreateDto dto = CompanyCreateDto.builder()
                .name("Alpha Logistics")
                .build();

        when(companyRepository.existsByName("Alpha Logistics"))
                .thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.create(dto)
        );

        assertEquals(
                "Company with this name already exists: Alpha Logistics",
                ex.getMessage()
        );

        verify(companyRepository, never()).save(any());
    }

    @Test
    void delete_existingCompany_deletesAndReturnsDto() {
        when(companyRepository.findById(1))
                .thenReturn(Optional.of(companyA));

        CompanyDto result = companyService.delete(1);

        assertEquals(1, result.getIdCompany());
        assertEquals("Alpha Logistics", result.getName());

        verify(companyRepository).delete(companyA);
    }

    @Test
    void delete_missingCompany_throwsException() {
        when(companyRepository.findById(50))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.delete(50)
        );

        assertEquals("Company not found: 50", ex.getMessage());
    }
}