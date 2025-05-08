package com.taass.user_service.controller;

import com.taass.user_service.model.Client;
import com.taass.user_service.service.ClientService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing operations on Clients.
 * Provides REST endpoints to add, retrieve, and delete Clients.
 */

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    /**
     * Constructor to initialize the ClientController with the ClientService.
     *
     * @param clientService the service for managing Clients
     */
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Adds a new Client.
     *
     * @param client the Client to add
     * @return the added Client
     */
    @PostMapping
    public Client addClient(Client client) {
        return clientService.addClient(client);
    }

    /**
     * Retrieves a Client by email.
     *
     * @param email the email of the Client to retrieve
     * @return the Client corresponding to the email
     */
    @GetMapping
    public Client getClient(String email) {
        return clientService.getClientByEmail(email);
    }

    /**
     * Deletes a Client by email.
     *
     * @param email the email of the Client to delete
     */
    @DeleteMapping
    public void deleteClient(String email) {
        clientService.deleteClientWithEmail(email);
    }

}
