package com.transport.transportcompany.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CompanyCreateDto {

    @NotBlank
    @Size(max = 45)
    private String name;
}
