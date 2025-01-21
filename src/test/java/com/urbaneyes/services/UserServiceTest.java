package com.urbaneyes.service;

import com.urbaneyes.model.User;
import com.urbaneyes.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "John Doe", "johndoe@example.com", "password123");
    }

    @Test
    void shouldRegisterUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldLoginUserSuccessfully() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String loginResponse = userService.loginUser(user);

        assertEquals("Login successful", loginResponse);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void shouldThrowExceptionForInvalidLogin() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User invalidUser = new User(null, null, "johndoe@example.com", "wrongpassword");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(invalidUser);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void shouldGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> fetchedUser = userService.getUserById(1L);

        assertTrue(fetchedUser.isPresent());
        assertEquals("John Doe", fetchedUser.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionForNonExistentUserById() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> fetchedUser = userService.getUserById(2L);

        assertFalse(fetchedUser.isPresent());
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void shouldUpdateUser() {
        User updatedUserDetails = new User(null, "Jane Doe", "janedoe@example.com", "newpassword");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(1L, updatedUserDetails);

        assertEquals("Jane Doe", updatedUser.getUsername());
        assertEquals("janedoe@example.com", updatedUser.getEmail());
        assertEquals("newpassword", updatedUser.getPassword());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User updatedUserDetails = new User(null, "Jane Doe", "janedoe@example.com", "newpassword");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(2L, updatedUserDetails);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.existsById(2L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(2L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(2L);
    }
}
