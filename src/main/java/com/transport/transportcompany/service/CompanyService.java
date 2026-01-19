package com.transport.transportcompany.service;

import com.transport.transportcompany.dto.CompanyCreateDto;
import com.transport.transportcompany.dto.CompanyDto;
import com.transport.transportcompany.dto.CompanyRevenueDto;

import java.util.List;

public interface CompanyService {
    List<CompanyDto> getAll();
    CompanyDto getById(Integer id);
    CompanyDto create(CompanyCreateDto dto);
    CompanyDto delete(Integer id);

    List<CompanyDto> getAllSortedByName();

    List<CompanyRevenueDto> getCompaniesByRevenue();

}
