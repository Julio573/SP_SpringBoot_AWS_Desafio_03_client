package com.example.client.service;

import com.example.client.entities.Client;
import com.example.client.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    Client client = new Client();

    @Transactional
    public Client save(Client client) {
        log.info("Registering a new client {}", client);
        return clientRepository.save(client);
    }

    @Transactional
    public Client findById(Long id) {
        log.info("Finding a client by id {}", id);
        return clientRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Client> findAll() {
        log.info("Finding all clients");
        return clientRepository.findAll();
    }

    @Transactional
    public Client updatePassword(Long id, String newPassword) {
        client.setPassword(newPassword);
        return client;
    }

    @Transactional
    public boolean confirmPassword(Long id, String password) {
        return password.equals(clientRepository.findById(id).get().getPassword());
    }

}
