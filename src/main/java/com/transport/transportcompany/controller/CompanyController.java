package com.transport.transportcompany.controller;

import com.transport.transportcompany.dto.CompanyCreateDto;
import com.transport.transportcompany.dto.CompanyDto;
import com.transport.transportcompany.dto.CompanyRevenueDto;
import com.transport.transportcompany.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyDto> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable Integer id) {
        return companyService.getById(id);
    }

    @PostMapping
    public CompanyDto create(@Valid @RequestBody CompanyCreateDto dto) {
        return companyService.create(dto);
    }

    @DeleteMapping("/{id}")
    public CompanyDto delete(@PathVariable Integer id) {
        return companyService.delete(id);
    }

    @GetMapping("/sorted-by-name")
    public List<CompanyDto> getAllSortedByName() {
        return companyService.getAllSortedByName();
    }

    @GetMapping("/revenue")
    public List<CompanyRevenueDto> getCompaniesByRevenue() {
        return companyService.getCompaniesByRevenue();
    }
}
