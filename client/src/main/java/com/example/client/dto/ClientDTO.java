package com.example.client.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    @NotBlank
    private String name;

    @Email(message = "Invalid email format")
    private String email;
}
