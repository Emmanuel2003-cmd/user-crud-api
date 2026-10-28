package com.emmanuel.usercrud.service;

import com.emmanuel.usercrud.dto.UserRequestDTO;
import com.emmanuel.usercrud.dto.UserResponseDTO;
import com.emmanuel.usercrud.entity.User;
import com.emmanuel.usercrud.exception.EmailAlreadyExistsException;
import com.emmanuel.usercrud.exception.ResourceNotFoundException;
import com.emmanuel.usercrud.mapper.UserMapper;
import com.emmanuel.usercrud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = new UserMapper();

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDTO request;

    @BeforeEach
    void setUp() {
        user = new User("Emmanuel", "emmanuel@test.com", "3001234567");
        user.setId(1L);
        request = new UserRequestDTO("Emmanuel", "emmanuel@test.com", "3001234567");
    }

    @Test
    void findAll_debeRetornarListaDeUsuarios() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDTO> result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals("Emmanuel", result.get(0).name());
    }

    @Test
    void findById_cuandoExiste_debeRetornarUsuario() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("emmanuel@test.com", result.email());
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarExcepcion() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(99L));
    }

    @Test
    void create_conEmailNuevo_debeGuardarUsuario() {
        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.create(request);

        assertEquals(1L, result.id());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_conEmailDuplicado_debeLanzarExcepcion() {
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.create(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void update_cuandoExiste_debeActualizarDatos() {
        UserRequestDTO cambios = new UserRequestDTO("Emmanuel Ocoro", "nuevo@test.com", "3110000000");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmailAndIdNot(cambios.email(), 1L)).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        UserResponseDTO result = userService.update(1L, cambios);

        assertEquals("Emmanuel Ocoro", result.name());
        assertEquals("nuevo@test.com", result.email());
    }

    @Test
    void delete_cuandoExiste_debeEliminarUsuario() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarExcepcion() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.delete(99L));
        verify(userRepository, never()).delete(any(User.class));
    }
}
