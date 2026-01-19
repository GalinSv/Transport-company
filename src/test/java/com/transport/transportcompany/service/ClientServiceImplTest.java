package com.transport.transportcompany.service;

import com.transport.transportcompany.data.entity.Client;
import com.transport.transportcompany.data.entity.Company;
import com.transport.transportcompany.data.repo.ClientRepository;
import com.transport.transportcompany.data.repo.CompanyRepository;
import com.transport.transportcompany.dto.ClientCreateDto;
import com.transport.transportcompany.dto.ClientDto;
import com.transport.transportcompany.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void create_shouldSaveClient() {
        // given
        ClientCreateDto dto = mock(ClientCreateDto.class);
        when(dto.getName()).thenReturn("TechCorp");
        when(dto.getCompanyId()).thenReturn(1);

        Company company = new Company();
        company.setIdCompany(1);

        Client savedClient = new Client();
        savedClient.setIdClient(1);
        savedClient.setName("TechCorp");
        savedClient.setCompany(company);

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // when
        ClientDto result = clientService.createClient(dto);

        // then
        assertNotNull(result);
        assertEquals("TechCorp", result.getName());

        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void getById_existingClient_shouldReturnDto() {

        Company company = new Company();
        company.setIdCompany(1);

        Client client = new Client();
        client.setIdClient(1);
        client.setName("Client A");
        client.setCompany(company);

        when(clientRepository.findById(1))
                .thenReturn(Optional.of(client));


        ClientDto result = clientService.getClientById(1);


        assertNotNull(result);
        assertEquals("Client A", result.getName());
        assertEquals(1, result.getCompanyId());
    }

    @Test
    void delete_existingClient_shouldCallRepositoryDelete() {

        Client client = new Client();
        client.setIdClient(1);

        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        clientService.deleteClient(1);


        verify(clientRepository).delete(client);
    }
    @Test
    void getAllClients_shouldReturnListOfDtos() {

        Company company = new Company();
        company.setIdCompany(1);

        Client c1 = new Client();
        c1.setIdClient(1);
        c1.setName("Client A");
        c1.setCompany(company);

        Client c2 = new Client();
        c2.setIdClient(2);
        c2.setName("Client B");
        c2.setCompany(company);

        when(clientRepository.findAll())
                .thenReturn(List.of(c1, c2));


        List<ClientDto> result = clientService.getAllClients();


        assertEquals(2, result.size());
        assertEquals("Client A", result.get(0).getName());
        assertEquals("Client B", result.get(1).getName());

        verify(clientRepository).findAll();
    }
    @Test
    void getById_nonExistingClient_shouldThrowException() {

        when(clientRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> clientService.getClientById(99));

        verify(clientRepository).findById(99);
    }
    @Test
    void delete_nonExistingClient_shouldThrowExceptionAndNotDelete() {

        when(clientRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> clientService.deleteClient(99));

        verify(clientRepository, never()).delete(any());
    }
    @Test
    void getAllClients_whenNoClients_shouldReturnEmptyList() {

        when(clientRepository.findAll())
                .thenReturn(List.of());

        List<ClientDto> result = clientService.getAllClients();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(clientRepository).findAll();
    }


}




