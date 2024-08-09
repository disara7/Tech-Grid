package com.ecom.service.impl;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceImplIntegrationTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        // Clean up database or setup initial data if needed
    }

    @Test
    public void testSaveProduct() throws IOException {
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setStock(10);
        product.setCategory("Electronics");
        product.setIsActive(true);
        product.setDiscount(5);

        MultipartFile image = mock(MultipartFile.class);
        when(image.getOriginalFilename()).thenReturn("testImage.png");
        when(image.isEmpty()).thenReturn(false);

        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getTitle());

        // Verify file operations
        File saveFile = new File("src/main/resources/static/img/product_img/testImage.png");
        Path path = Paths.get(saveFile.getAbsolutePath());
        assertTrue(Files.exists(path));

        // Clean up test file
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    public void testUpdateProduct() throws IOException {
        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setTitle("Existing Product");
        existingProduct.setPrice(150.0);
        productRepository.save(existingProduct);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setTitle("Updated Product");
        updatedProduct.setPrice(120.0);

        MultipartFile image = mock(MultipartFile.class);
        when(image.getOriginalFilename()).thenReturn("updatedImage.png");
        when(image.isEmpty()).thenReturn(false);

        Product result = productService.updateProduct(updatedProduct, image);

        assertNotNull(result);
        assertEquals("Updated Product", result.getTitle());

        // Verify file operations
        File saveFile = new File("src/main/resources/static/img/product_img/updatedImage.png");
        Path path = Paths.get(saveFile.getAbsolutePath());
        assertTrue(Files.exists(path));

        // Clean up test file
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
