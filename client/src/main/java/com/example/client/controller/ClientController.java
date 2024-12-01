package com.example.client.controller;

import com.example.client.entities.Client;
import com.example.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Slf4j
public class ClientController {

    private final ClientService clientService;
    Client client = new Client();

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        log.info("Request to create client {}", client);
        Client newClient = clientService.save(client);
        return ResponseEntity.ok(newClient);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Client>> listAllClients() {
        log.info("Request to list all clients");
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        log.info("Request to get client {}", id);
        Client client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Client> updatePassword(@PathVariable Long id, @RequestParam String password, @RequestParam String newPassword) {
        log.info("Request to update password {}", id);

        boolean validPassword = clientService.confirmPassword(id, password);

        if (!validPassword) {
            log.warn("Invalid password");
            return ResponseEntity.badRequest().build();
        }
        clientService.updatePassword(id, newPassword);
        log.info("Password updated successfully for user with ID: {}", id);
        return ResponseEntity.ok().body(client);
    }
}
