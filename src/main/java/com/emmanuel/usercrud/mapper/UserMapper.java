package com.emmanuel.usercrud.mapper;

import com.emmanuel.usercrud.dto.UserRequestDTO;
import com.emmanuel.usercrud.dto.UserResponseDTO;
import com.emmanuel.usercrud.entity.User;
import org.springframework.stereotype.Component;

/**
 * Convierte entre la entidad User y sus DTOs.
 */
@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        return new User(dto.name(), dto.email(), dto.phone());
    }

    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getCreatedAt()
        );
    }

    public void updateEntity(User user, UserRequestDTO dto) {
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
    }
}
