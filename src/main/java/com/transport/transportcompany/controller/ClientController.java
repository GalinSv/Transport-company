package com.transport.transportcompany.controller;

import com.transport.transportcompany.dto.ClientCreateDto;
import com.transport.transportcompany.dto.ClientDto;
import com.transport.transportcompany.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientDto getById(@PathVariable Integer id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public ClientDto create(@RequestBody ClientCreateDto dto) {
        return clientService.createClient(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        clientService.deleteClient(id);
    }
}
