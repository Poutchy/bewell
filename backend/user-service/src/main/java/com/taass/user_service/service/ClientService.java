package com.taass.user_service.service;

import com.taass.user_service.dto.ClientDTO;
import com.taass.user_service.dto.ClientRequest;
import com.taass.user_service.model.Client;
import com.taass.user_service.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing Client operations.
 * Provides methods to add, retrieve, and delete Clients.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;

    /**
     * Adds a new Client to the repository.
     *
     * @param clientRequest the Client to add
     */
    public void addClient(ClientRequest clientRequest) {
        Client client = Client.builder()
                .name(clientRequest.getName())
                .surname(clientRequest.getSurname())
                .email(clientRequest.getEmail())
                .phone_number(clientRequest.getPhone_number())
                .address(clientRequest.getAddress())
                .birthday(clientRequest.getBirthday())
                .gender(clientRequest.getGender())
                .registration_date(clientRequest.getRegistration_date())
                .loyalty_point(clientRequest.getLoyalty_point())
                .flag(clientRequest.getFlag())
                .build();
        clientRepository.save(client);
        log.info("Client added: {}", client.getId());
    }

    /**
     * Retrieves a Client by their email.
     *
     * @param email the email of the Client to retrieve
     * @return the Client corresponding to the given email
     */
    public ClientDTO getClientByEmail(String email) {
        Client client = clientRepository.findClientByEmail(email);
        return mapToClientDTO(client);
    }

    /**
     * Retrieves a Client by their email.
     *
     * @param id the id of the Client to retrieve
     * @return the Client corresponding to the given id
     */
    public ClientDTO getClientById(int id) {
        Client client = clientRepository.findClientById(id);
        return mapToClientDTO(client);
    }

    /**
     * Retrieves all Client.
     *
     * @return All the Client
     */
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(this::mapToClientDTO).toList();
    }

    private ClientDTO mapToClientDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .email(client.getEmail())
                .phone_number(client.getPhone_number())
                .address(client.getAddress())
                .birthday(client.getBirthday())
                .gender(client.getGender())
                .registration_date(client.getRegistration_date())
                .loyalty_point(client.getLoyalty_point())
                .flag(client.getFlag())
                .build();
    }


    /**
     * Deletes a Client by their email.
     *
     * @param email the email of the Client to delete
     */
    public void deleteClientWithEmail(String email) {
        clientRepository.deleteClientByEmail(email);
    }

    @Transactional
    public void deleteClientWithId(Integer id) {
        clientRepository.deleteClientById(id);
    }

    /**
     * Update a Client.
     *
     * @param client the information of the Client to update
     */
    public ClientDTO update(Client client) {
        Client updatedClient = clientRepository.save(client);
        return mapToClientDTO(updatedClient);
    }
}
