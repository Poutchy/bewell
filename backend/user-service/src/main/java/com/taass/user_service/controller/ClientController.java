package com.taass.user_service.controller;

import com.taass.user_service.dto.ClientDTO;
import com.taass.user_service.dto.ClientRequest;
import com.taass.user_service.model.Client;
import com.taass.user_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing operations on Clients.
 * Provides REST endpoints to add, retrieve, and delete Clients.
 */

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    /**
     * Adds a new Client.
     *
     * @param clientRequest the Client to add
     */
    @PostMapping("/addClient")
    @ResponseStatus(HttpStatus.CREATED)
    public void addClient(@RequestBody ClientRequest clientRequest) {
        clientService.addClient(clientRequest);
    }

    /**
     * Retrieves a Client by email.
     *
     * @param email the email of the Client to retrieve
     * @return the Client corresponding to the email
     */
    @GetMapping("/{email}")
    public ClientDTO getClient(@PathVariable String email) {
        return clientService.getClientByEmail(email);
    }

    /**
     * Retrieves a Client by id.
     *
     * @param id the id of the Client to retrieve
     * @return the Client corresponding to the id
     */
    @GetMapping("/getClient/{id}")
    public ClientDTO getClientWithId(@PathVariable int id) {
        return clientService.getClientById(id);
    }

    /**
     * Retrieves all Client.
     *
     * @return All the Client
     */
    @GetMapping("/getAllClients")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDTO> getAllClient() {
        return clientService.getAllClients();
    }


    /**
     * Deletes a Client by email.
     *
     * @param email the email of the Client to delete
     */
    @DeleteMapping("/delete/{email}")
    public void deleteClient(@PathVariable String email) {
        clientService.deleteClientWithEmail(email);
    }

    /**
     * Updates a Client.
     *
     * @param client the Client to update
     * @return the updated Client
     */
    @PutMapping("/update")
    public ClientDTO updateClient(@RequestBody Client client) {
        return clientService.update(client);
    }

}
