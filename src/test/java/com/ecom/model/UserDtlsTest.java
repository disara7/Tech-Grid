package com.ecom.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtlsTest {

    @Test
    public void testDefaultConstructor() {
        UserDtls user = new UserDtls();
        
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getMobileNumber());
        assertNull(user.getEmail());
        assertNull(user.getAddress());
        assertNull(user.getCity());
        assertNull(user.getState());
        assertNull(user.getPincode());
        assertNull(user.getPassword());
        assertNull(user.getProfileImage());
    }

    @Test
    public void testAllArgsConstructor() {
        UserDtls user = new UserDtls(1, "John Doe", "1234567890", "john.doe@example.com", 
                                     "123 Main St", "Cityville", "Stateburg", "12345", 
                                     "password123", "profile.jpg");
        
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("1234567890", user.getMobileNumber());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("Cityville", user.getCity());
        assertEquals("Stateburg", user.getState());
        assertEquals("12345", user.getPincode());
        assertEquals("password123", user.getPassword());
        assertEquals("profile.jpg", user.getProfileImage());
    }

    @Test
    public void testGettersAndSetters() {
        UserDtls user = new UserDtls();
        
        user.setId(2);
        user.setName("Jane Doe");
        user.setMobileNumber("0987654321");
        user.setEmail("jane.doe@example.com");
        user.setAddress("456 Elm St");
        user.setCity("Townsville");
        user.setState("Regionland");
        user.setPincode("67890");
        user.setPassword("newpassword123");
        user.setProfileImage("newprofile.jpg");

        assertEquals(2, user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("0987654321", user.getMobileNumber());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("456 Elm St", user.getAddress());
        assertEquals("Townsville", user.getCity());
        assertEquals("Regionland", user.getState());
        assertEquals("67890", user.getPincode());
        assertEquals("newpassword123", user.getPassword());
        assertEquals("newprofile.jpg", user.getProfileImage());
    }

    @Test
    public void testSetUsernameThrowsException() {
        UserDtls user = new UserDtls();
        
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            user.setUsername("user");
        });

        assertEquals("Unimplemented method 'setUsername'", exception.getMessage());
    }

    @Test
    public void testGetUsernameThrowsException() {
        UserDtls user = new UserDtls();
        
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            user.getUsername();
        });

        assertEquals("Unimplemented method 'getUsername'", exception.getMessage());
    }
}
