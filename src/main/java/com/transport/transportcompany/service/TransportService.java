package com.transport.transportcompany.service;

import com.transport.transportcompany.dto.TransportCreateDto;
import com.transport.transportcompany.dto.TransportDto;
import com.transport.transportcompany.dto.TransportUpdateDto;

import java.util.List;

public interface TransportService {

    List<TransportDto> getAll();

    TransportDto getById(Integer id);

    TransportDto create(TransportCreateDto dto);
    TransportDto update(Integer id, TransportUpdateDto dto);

    void markAsPaid(Integer id);

    void delete(Integer id);
    List<TransportDto> getByDestination(String destination);
}
