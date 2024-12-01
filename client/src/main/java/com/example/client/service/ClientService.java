package com.example.client.service;

import com.example.client.dto.ClientDTO;
import com.example.client.dto.mapper.ClientMapper;
import com.example.client.entities.Client;
import com.example.client.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    Client client = new Client();

    @Transactional
    public ClientDTO save(Client client) {
        log.info("Registering a new client {}", client);
        Client newClient = clientRepository.save(client);
        return ClientMapper.toDTO(newClient);
    }

    @Transactional
    public ClientDTO findById(Long id) {
        log.info("Finding a client by id {}", id);
        Client client = clientRepository.findById(id).orElse(null);
        return ClientMapper.toDTO(client);
    }

    @Transactional
    public List<ClientDTO> findAll() {
        log.info("Finding all clients");
        List<Client> client = clientRepository.findAll();
        return client.stream()
                .map(ClientMapper::toDTO)
                .collect(Collectors.toList());
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
