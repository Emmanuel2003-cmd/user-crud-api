package com.emmanuel.usercrud.dto;

import java.time.LocalDateTime;

/**
 * Datos del usuario que la API expone como JSON.
 */
public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String phone,
        LocalDateTime createdAt
) {
}
