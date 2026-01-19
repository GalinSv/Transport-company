package com.transport.transportcompany.dto;

import com.transport.transportcompany.data.entity.TransportStatus;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class TransportUpdateDto {
    private Double price;
    private TransportStatus status;
    private Boolean paid;
}