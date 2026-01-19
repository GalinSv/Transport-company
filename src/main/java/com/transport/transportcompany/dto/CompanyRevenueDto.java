package com.transport.transportcompany.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyRevenueDto {
    private Integer companyId;
    private String name;
    private Double revenue;
}
