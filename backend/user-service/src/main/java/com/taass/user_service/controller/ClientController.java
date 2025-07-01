package com.taass.user_service.controller;

import com.taass.user_service.dto.ClientDTO;
import com.taass.user_service.dto.ClientRequest;
import com.taass.user_service.model.Client;
import com.taass.user_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing operations on Clients.
 * Provides REST endpoints to add, retrieve, and delete Clients.
 */

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    /**
     * Adds a new Client.
     *
     * @param clientRequest the Client to add
     */
    @PostMapping("/addClient")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientRequest clientRequest) {
        ClientDTO client = clientService.addClient(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    /**
     * Retrieves a Client by email.
     *
     * @param email the email of the Client to retrieve
     * @return the Client corresponding to the email
     */
    @GetMapping("/{email}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable String email) {
        ClientDTO client =  clientService.getClientByEmail(email);
        return ResponseEntity.ok(client);
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
    public ResponseEntity<List<ClientDTO>> getAllClient() {
        List<ClientDTO> clients =  clientService.getAllClients();
        return  ResponseEntity.ok(clients);
    }


    /**
     * Deletes a Client by email.
     *
     * @param email the email of the Client to delete
     */
    @DeleteMapping("/delete/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable String email) {
        clientService.deleteClientWithEmail(email);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable Integer id) {
        clientService.deleteClientWithId(id);
    }

    /**
     * Updates a Client.
     *
     * @param client the Client to update
     * @return the updated Client
     */
    @PutMapping("/update")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody Client client) {
        ClientDTO updatedClient = clientService.update(client);
        return  ResponseEntity.ok(updatedClient);
    }

}
