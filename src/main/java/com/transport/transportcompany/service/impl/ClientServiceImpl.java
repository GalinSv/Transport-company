package com.transport.transportcompany.service.impl;

import com.transport.transportcompany.data.entity.Client;
import com.transport.transportcompany.data.entity.Company;
import com.transport.transportcompany.data.repo.ClientRepository;
import com.transport.transportcompany.data.repo.CompanyRepository;
import com.transport.transportcompany.dto.ClientCreateDto;
import com.transport.transportcompany.dto.ClientDto;
import com.transport.transportcompany.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;

    public ClientServiceImpl(ClientRepository clientRepository,
                             CompanyRepository companyRepository) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
        return toDto(client);
    }

    @Override
    public ClientDto createClient(ClientCreateDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + dto.getCompanyId()));

        Client saved = clientRepository.save(
                Client.builder()
                        .name(dto.getName().trim())
                        .company(company)
                        .build()
        );

        return toDto(saved);
    }

    @Override
    public void deleteClient(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
        clientRepository.delete(client);
    }

    private ClientDto toDto(Client c) {
        return ClientDto.builder()
                .idClient(c.getIdClient())
                .name(c.getName())
                .companyId(c.getCompany().getIdCompany())
                .build();
    }
}
