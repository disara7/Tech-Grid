package com.ecom.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void testDefaultConstructor() {
        Category category = new Category();
        
        assertNotNull(category);
        assertEquals(0, category.getId());
        assertNull(category.getName());
        assertNull(category.getImageName());
        assertNull(category.getIsActive());
    }

    @Test
    public void testAllArgsConstructor() {
        Category category = new Category(1, "Electronics", "image.png", true);
        
        assertNotNull(category);
        assertEquals(1, category.getId());
        assertEquals("Electronics", category.getName());
        assertEquals("image.png", category.getImageName());
        assertTrue(category.getIsActive());
    }

    @Test
    public void testGettersAndSetters() {
        Category category = new Category();
        
        category.setId(2);
        category.setName("Home Appliances");
        category.setImageName("home.png");
        category.setIsActive(false);

        assertEquals(2, category.getId());
        assertEquals("Home Appliances", category.getName());
        assertEquals("home.png", category.getImageName());
        assertFalse(category.getIsActive());
    }
}
