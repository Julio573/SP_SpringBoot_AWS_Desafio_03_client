package com.example.client.dto.mapper;

import com.example.client.dto.ClientDTO;
import com.example.client.entities.Client;

public class ClientMapper {

    public static ClientDTO toDTO(Client client) {
        return new ClientDTO(client.getId(), client.getUsername(), client.getEmail());
    }
}
