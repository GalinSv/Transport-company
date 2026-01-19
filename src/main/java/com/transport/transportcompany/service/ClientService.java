package com.transport.transportcompany.service;

import com.transport.transportcompany.dto.ClientCreateDto;
import com.transport.transportcompany.dto.ClientDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getAllClients();

    ClientDto getClientById(Integer id);

    ClientDto createClient(ClientCreateDto dto);

    void deleteClient(Integer id);
}
