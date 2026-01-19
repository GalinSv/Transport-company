package com.transport.transportcompany.controller;

import com.transport.transportcompany.dto.QualificationCreateDto;
import com.transport.transportcompany.dto.QualificationDto;
import com.transport.transportcompany.service.QualificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qualifications")
public class QualificationController {

    private final QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    @GetMapping
    public List<QualificationDto> getAll() {
        return qualificationService.getAllQualifications();
    }

    @GetMapping("/{id}")
    public QualificationDto getById(@PathVariable Integer id) {
        return qualificationService.getQualificationById(id);
    }

    @PostMapping
    public QualificationDto create(@RequestBody QualificationCreateDto dto) {
        return qualificationService.createQualification(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        qualificationService.deleteQualification(id);
    }
}
