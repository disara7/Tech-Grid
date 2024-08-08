package com.ecom.service;
import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetUserByUsername() {
        MockitoAnnotations.openMocks(this);

        UserDtls user = new UserDtls();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<UserDtls> foundUser = userService.getUserByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertTrue(foundUser.get().getUsername().equals("testuser"));
    }

    //Test the scenario where a user with the given username does not exist

    @Test
    public void testGetUserByUsernameNotFound() {
        MockitoAnnotations.openMocks(this);

        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        Optional<UserDtls> foundUser = userService.getUserByUsername("nonexistentuser");

        assertFalse(foundUser.isPresent());
    }

    //Test the scenario where a new user is created successfully.

    @Test
    public void testCreateUser() {
        MockitoAnnotations.openMocks(this);

        UserDtls newUser = new UserDtls();
        newUser.setUsername("newuser");
        when(userRepository.save(any(UserDtls.class))).thenReturn(newUser);

        UserDtls createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals("newuser", createdUser.getUsername());
    }


}
