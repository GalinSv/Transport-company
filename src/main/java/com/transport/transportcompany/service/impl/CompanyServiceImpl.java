package com.transport.transportcompany.service.impl;

import com.transport.transportcompany.data.entity.Company;
import com.transport.transportcompany.data.repo.CompanyRepository;
import com.transport.transportcompany.dto.CompanyCreateDto;
import com.transport.transportcompany.dto.CompanyDto;
import com.transport.transportcompany.dto.CompanyRevenueDto;
import com.transport.transportcompany.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyDto> getAllSortedByName() {
        return companyRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<CompanyRevenueDto> getCompaniesByRevenue() {
        return companyRepository.findCompaniesWithRevenue()
                .stream()
                .map(row -> CompanyRevenueDto.builder()
                        .companyId(((Company) row[0]).getIdCompany())
                        .name(((Company) row[0]).getName())
                        .revenue((Double) row[1])
                        .build())
                .toList();
    }

    @Override
    public List<CompanyDto> getAll() {
        return companyRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CompanyDto getById(Integer id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + id));
        return toDto(company);
    }

    @Override
    public CompanyDto create(CompanyCreateDto dto) {
        String name = dto.getName().trim();

        if (companyRepository.existsByName(name)) {
            throw new IllegalArgumentException("Company with this name already exists: " + name);
        }

        Company saved = companyRepository.save(
                Company.builder()
                        .name(name)
                        .build()
        );

        return toDto(saved);
    }

    @Override
    public CompanyDto delete(Integer id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + id));

        companyRepository.delete(company);
        return toDto(company);
    }

    private CompanyDto toDto(Company c) {
        return CompanyDto.builder()
                .idCompany(c.getIdCompany())
                .name(c.getName())
                .build();
    }
}
