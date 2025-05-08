package com.taass.user_service.service;

import com.taass.user_service.model.Client;
import com.taass.user_service.repository.ClientRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Client operations.
 * Provides methods to add, retrieve, and delete Clients.
 */
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    /**
     * Constructor to initialize the ClientService with the ClientRepository.
     *
     * @param clientRepository the repository for managing Client entities
     */
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Adds a new Client to the repository.
     *
     * @param client the Client to add
     * @return the added Client
     */
    public Client addClient(Client client) {
        System.out.println("Adding client: " + client);
        return clientRepository.save(client);
    }

    /**
     * Retrieves a Client by their email.
     *
     * @param email the email of the Client to retrieve
     * @return the Client corresponding to the given email
     */
    public Client getClientByEmail(String email) {
        return clientRepository.findClientByEmail(email);
    }


    /**
     * Deletes a Client by their email.
     *
     * @param email the email of the Client to delete
     */
    public void deleteClientWithEmail(String email) {
        clientRepository.deleteClientByEmail(email);
    }
}
