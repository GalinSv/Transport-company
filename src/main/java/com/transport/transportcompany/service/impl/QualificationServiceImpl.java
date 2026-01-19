package com.transport.transportcompany.service.impl;

import com.transport.transportcompany.data.entity.Qualification;
import com.transport.transportcompany.data.repo.QualificationRepository;
import com.transport.transportcompany.dto.QualificationCreateDto;
import com.transport.transportcompany.dto.QualificationDto;
import com.transport.transportcompany.service.QualificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;

    public QualificationServiceImpl(QualificationRepository qualificationRepository) {
        this.qualificationRepository = qualificationRepository;
    }

    @Override
    public List<QualificationDto> getAllQualifications() {
        return qualificationRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public QualificationDto getQualificationById(Integer id) {
        Qualification q = qualificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Qualification not found: " + id));
        return toDto(q);
    }

    @Override
    public QualificationDto createQualification(QualificationCreateDto dto) {
        Qualification saved = qualificationRepository.save(
                Qualification.builder()
                        .typeOfQualification(dto.getTypeOfQualification().trim())
                        .build()
        );
        return toDto(saved);
    }

    @Override
    public void deleteQualification(Integer id) {
        Qualification q = qualificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Qualification not found: " + id));
        qualificationRepository.delete(q);
    }

    private QualificationDto toDto(Qualification q) {
        return QualificationDto.builder()
                .idQualification(q.getIdQualification())
                .typeOfQualification(q.getTypeOfQualification())
                .build();
    }
}
