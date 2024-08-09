package com.ecom.service;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

        System.out.println("testGetUserByEmail - Found User: " + foundUser);
        if (foundUser.isPresent()) {
            System.out.println("User Email: " + foundUser.get().getEmail());
        }

        assertTrue(foundUser.isPresent());
        assertEquals("testuser@example.com", foundUser.get().getEmail());
    }

    @Test
    public void testGetUserByEmailNotFound() {
        when(userRepository.findByEmail("nonexistentuser@example.com")).thenReturn(Optional.empty());

        Optional<UserDtls> foundUser = userService.getUserByEmail("nonexistentuser@example.com");

        System.out.println("testGetUserByEmailNotFound - Found User: " + foundUser);

        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testCreateUser() {
        UserDtls newUser = new UserDtls();
        newUser.setEmail("newuser@example.com");
        when(userRepository.save(any(UserDtls.class))).thenReturn(newUser);

        UserDtls createdUser = userService.createUser(newUser);

        System.out.println("testCreateUser - Created User: " + createdUser);
        System.out.println("Created User Email: " + createdUser.getEmail());

        assertNotNull(createdUser);
        assertEquals("newuser@example.com", createdUser.getEmail());
    }
}
