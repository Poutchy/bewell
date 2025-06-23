package com.taass.user_service.repository;

import com.taass.user_service.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Client entities.
 * Provides methods for CRUD operations and custom queries on the Client database table.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Finds a Client by their email.
     *
     * @param email the email of the Client to find
     * @return the Client with the specified email
     */
    Client findClientByEmail(String email);

    /**
     * Finds a Client by their id.
     *
     * @param id the email of the Client to find
     * @return the Client with the specified id
     */
    Client findClientById(int id);


    /**
     * Deletes a Client by their email.
     *
     * @param email the email of the Client to delete
     */
    void deleteClientByEmail(String email);
}
