package com.taass.user_service.controller;

import com.taass.user_service.model.Client;
import com.taass.user_service.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @GetMapping("/{email}")
    public Client getClient(@PathVariable String email) {
        return clientService.getClientByEmail(email);
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @DeleteMapping("/{email}")
    public void deleteClient(@PathVariable String email) {
        clientService.deleteClientWithEmail(email);
    }
}