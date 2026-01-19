package com.transport.transportcompany.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientDto {
    private Integer idClient;
    private String name;
    private Integer companyId;
}
