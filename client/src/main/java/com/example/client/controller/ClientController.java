package com.example.client.controller;

import com.example.client.dto.ClientDTO;
import com.example.client.entities.Client;
import com.example.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
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


    @Operation(summary = "Register a new client", description = "resource to register new clients",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client registered successfully",
                            content = @Content(mediaType = "application/JSON", schema = @Schema(implementation = ClientDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Email address already registered",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDTO.class))),
                    @ApiResponse(responseCode = "422", description = "invalid data input",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createClient(@RequestBody Client client) {
        log.info("Request to create client {}", client);
        ClientDTO newClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @Operation(summary = "Returns all clients", description = "Returns all clients registered on the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clients found successfully",
                            content = @Content(mediaType = "application/JSON", schema = @Schema(implementation = ClientDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Client database Empty",
                            content = @Content(mediaType = "application/json"))})
    @GetMapping("/list")
    public ResponseEntity<List<ClientDTO>> listAllClients() {
        log.info("Request to list all clients");
        List<ClientDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Gets an user by ID", description = "Gets an user by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found successfully",
                            content = @Content(mediaType = "application/JSON", schema = @Schema(implementation = ClientDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        log.info("Request to get client {}", id);
        ClientDTO client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Updates password", description = "Resource to update password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password updated successfully",
                            content = @Content(mediaType = "application/JSON", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Password does not match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
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

