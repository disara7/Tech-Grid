package com.ecom.service;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService; 
    
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByEmail() {
        UserDtls user = new UserDtls();
        user.setEmail("testuser@example.com");
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        Optional<UserDtls> foundUser = userService.getUserByEmail("testuser@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser@example.com", foundUser.get().getEmail());
    }

    @Test
    public void testGetUserByEmailNotFound() {
        when(userRepository.findByEmail("nonexistentuser@example.com")).thenReturn(Optional.empty());

        Optional<UserDtls> foundUser = userService.getUserByEmail("nonexistentuser@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testCreateUser() {
        UserDtls newUser = new UserDtls();
        newUser.setEmail("newuser@example.com");
        when(userRepository.save(any(UserDtls.class))).thenReturn(newUser);

        UserDtls createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals("newuser@example.com", createdUser.getEmail());
    }

    @Test
    public void testGetAllUsers() {
        UserDtls user1 = new UserDtls();
        UserDtls user2 = new UserDtls();
        List<UserDtls> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDtls> userList = userService.getAllUsers();

        assertNotNull(userList);
        assertEquals(2, userList.size());
    }

    @Test
    public void testGetAllUsersEmpty() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserDtls> userList = userService.getAllUsers();

        assertNotNull(userList);
        assertTrue(userList.isEmpty());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1);

        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetUserByIdInteger() {
        UserDtls user = new UserDtls();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDtls foundUser = userService.getUserById(1);

        assertNotNull(foundUser);
        assertEquals(1, foundUser.getId());
    }

    @Test
    public void testGetUserByIdIntegerNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        UserDtls foundUser = userService.getUserById(1);

        assertNull(foundUser);
    }
}
