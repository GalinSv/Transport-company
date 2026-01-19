package com.transport.transportcompany.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QualificationDto {
    private Integer idQualification;
    private String typeOfQualification;
}
