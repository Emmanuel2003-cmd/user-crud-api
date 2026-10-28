package com.emmanuel.usercrud.service;

import com.emmanuel.usercrud.dto.UserRequestDTO;
import com.emmanuel.usercrud.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> findAll();

    UserResponseDTO findById(Long id);

    UserResponseDTO create(UserRequestDTO request);

    UserResponseDTO update(Long id, UserRequestDTO request);

    void delete(Long id);
}
