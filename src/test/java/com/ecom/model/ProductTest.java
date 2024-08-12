package com.ecom.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testDefaultConstructor() {
        Product product = new Product();
        
        assertNotNull(product);
        assertEquals(0, product.getId());
        assertNull(product.getTitle());
        assertNull(product.getDescription());
        assertNull(product.getCategory());
        assertNull(product.getPrice());
        assertEquals(0, product.getStock());
        assertNull(product.getImage());
        assertEquals(0, product.getDiscount());
        assertNull(product.getDiscountPrice());
        assertNull(product.getIsActive());
    }

    @Test
    public void testAllArgsConstructor() {
        Product product = new Product(1, "Product Title", "Product Description", "Electronics", 
                                      99.99, 10, "image.png", 10, 89.99, true);

        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Product Title", product.getTitle());
        assertEquals("Product Description", product.getDescription());
        assertEquals("Electronics", product.getCategory());
        assertEquals(99.99, product.getPrice());
        assertEquals(10, product.getStock());
        assertEquals("image.png", product.getImage());
        assertEquals(10, product.getDiscount());
        assertEquals(89.99, product.getDiscountPrice());
        assertTrue(product.getIsActive());
    }

    @Test
    public void testGettersAndSetters() {
        Product product = new Product();
        
        product.setId(2);
        product.setTitle("New Product");
        product.setDescription("New Product Description");
        product.setCategory("Home Appliances");
        product.setPrice(149.99);
        product.setStock(20);
        product.setImage("newimage.png");
        product.setDiscount(15);
        product.setDiscountPrice(127.49);
        product.setIsActive(false);

        assertEquals(2, product.getId());
        assertEquals("New Product", product.getTitle());
        assertEquals("New Product Description", product.getDescription());
        assertEquals("Home Appliances", product.getCategory());
        assertEquals(149.99, product.getPrice());
        assertEquals(20, product.getStock());
        assertEquals("newimage.png", product.getImage());
        assertEquals(15, product.getDiscount());
        assertEquals(127.49, product.getDiscountPrice());
        assertFalse(product.getIsActive());
    }
}
