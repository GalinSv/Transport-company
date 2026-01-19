package com.transport.transportcompany.service;
import com.transport.transportcompany.data.entity.Qualification;
import com.transport.transportcompany.data.repo.QualificationRepository;
import com.transport.transportcompany.dto.QualificationCreateDto;
import com.transport.transportcompany.dto.QualificationDto;
import com.transport.transportcompany.service.impl.QualificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QualificationServiceImplTest {

    @Mock
    private QualificationRepository qualificationRepository;

    @InjectMocks
    private QualificationServiceImpl qualificationService;

    private Qualification qualificationA;
    private Qualification qualificationB;

    @BeforeEach
    void setUp() {
        qualificationA = Qualification.builder()
                .idQualification(1)
                .typeOfQualification("Driver")
                .build();

        qualificationB = Qualification.builder()
                .idQualification(2)
                .typeOfQualification("Mechanic")
                .build();
    }


    @Test
    void getAllQualifications_returnsAllMappedDtos() {
        when(qualificationRepository.findAll())
                .thenReturn(List.of(qualificationA, qualificationB));

        List<QualificationDto> result = qualificationService.getAllQualifications();

        assertEquals(2, result.size());
        assertEquals("Driver", result.get(0).getTypeOfQualification());
        assertEquals("Mechanic", result.get(1).getTypeOfQualification());

        verify(qualificationRepository).findAll();
    }


    @Test
    void getQualificationById_existingId_returnsDto() {
        when(qualificationRepository.findById(1))
                .thenReturn(Optional.of(qualificationA));

        QualificationDto result = qualificationService.getQualificationById(1);

        assertEquals(1, result.getIdQualification());
        assertEquals("Driver", result.getTypeOfQualification());
    }

    @Test
    void getQualificationById_missingId_throwsException() {
        when(qualificationRepository.findById(99))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> qualificationService.getQualificationById(99)
        );

        assertEquals("Qualification not found: 99", ex.getMessage());
    }


    @Test
    void createQualification_trimsAndSavesQualification() {
        QualificationCreateDto dto = QualificationCreateDto.builder()
                .typeOfQualification("  Dispatcher  ")
                .build();

        when(qualificationRepository.save(any(Qualification.class)))
                .thenAnswer(invocation -> {
                    Qualification saved = invocation.getArgument(0);
                    saved.setIdQualification(10);
                    return saved;
                });

        QualificationDto result = qualificationService.createQualification(dto);

        assertEquals(10, result.getIdQualification());
        assertEquals("Dispatcher", result.getTypeOfQualification());

        verify(qualificationRepository).save(any(Qualification.class));
    }


    @Test
    void deleteQualification_existingQualification_deletes() {
        when(qualificationRepository.findById(1))
                .thenReturn(Optional.of(qualificationA));

        qualificationService.deleteQualification(1);

        verify(qualificationRepository).delete(qualificationA);
    }

    @Test
    void deleteQualification_missingQualification_throwsException() {
        when(qualificationRepository.findById(50))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> qualificationService.deleteQualification(50)
        );

        assertEquals("Qualification not found: 50", ex.getMessage());

        verify(qualificationRepository, never()).delete(any());
    }
}
