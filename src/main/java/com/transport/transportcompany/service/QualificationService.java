package com.transport.transportcompany.service;

import com.transport.transportcompany.dto.QualificationCreateDto;
import com.transport.transportcompany.dto.QualificationDto;

import java.util.List;

public interface QualificationService {

    List<QualificationDto> getAllQualifications();

    QualificationDto getQualificationById(Integer id);

    QualificationDto createQualification(QualificationCreateDto dto);

    void deleteQualification(Integer id);
}
