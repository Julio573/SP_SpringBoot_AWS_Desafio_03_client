package com.example.client.controller;

import com.example.client.dto.ClientDTO;
import com.example.client.entities.Client;
import com.example.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createClient(@RequestBody Client client) {
        log.info("Request to create client {}", client);
        ClientDTO newClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ClientDTO>> listAllClients() {
        log.info("Request to list all clients");
        List<ClientDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        log.info("Request to get client {}", id);
        ClientDTO client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Client> updatePassword(@PathVariable Long id,
                                                 @RequestParam String password,
                                                 @RequestParam String newPassword) {
        log.info("Request to update password {}", id);

        if (password == null || password.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Invalid Password");
        }

        boolean validPassword = clientService.confirmPassword(id, password);

        if (!validPassword) {
            log.warn("Invalid password");
            throw new IllegalArgumentException("The current password is incorrect");
        }

        Client updatedClient = clientService.updatePassword(id, newPassword);
        log.info("Password updated successfully for user with ID: {}", id);
        return ResponseEntity.ok(updatedClient);
    }
    }

