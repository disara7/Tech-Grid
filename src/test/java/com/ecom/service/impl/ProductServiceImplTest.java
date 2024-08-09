package com.ecom.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;

public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct() {
        // Given
        Product product = new Product();
        product.setId(1);
        product.setTitle("Test Product");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Product savedProduct = productService.saveProduct(product);

        // Then
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getTitle());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testGetAllProducts() {
        // Given
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.getAllProducts();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteProduct() {
        // Given
        Product product = new Product();
        product.setId(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        // When
        Boolean result = productService.deleteProduct(1);

        // Then
        assertTrue(result);
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testGetProductById() {
        // Given
        Product product = new Product();
        product.setId(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When
        Product result = productService.getProductById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // Given
        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setImage("oldImage.png");
        existingProduct.setPrice(100.0);  // Ensure price is not null

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setImage("newImage.png");
        updatedProduct.setPrice(100.0);  // Ensure price is not null
        updatedProduct.setDiscount(10);

        MultipartFile image = mock(MultipartFile.class);
        when(image.getOriginalFilename()).thenReturn("newImage.png");
        when(image.isEmpty()).thenReturn(false);

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Product result = productService.updateProduct(updatedProduct, image);

        // Then
        assertNotNull(result);
        assertEquals("newImage.png", result.getImage());

        // Verify file operations
        File saveFile = new ClassPathResource("static/img/product_img").getFile();
        Path path = Path.of(saveFile.getAbsolutePath(), "newImage.png");

        // Clean up test file
        Files.deleteIfExists(path);
    }

    @Test
    public void testGetAllActiveProducts() {
        // Given
        Product product1 = new Product();
        product1.setIsActive(true);
        Product product2 = new Product();
        product2.setIsActive(true);
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findByIsActiveTrue()).thenReturn(products);

        // When
        List<Product> result = productService.getAllActiveProducts(null);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByIsActiveTrue();
    }

    @Test
    public void testGetAllActiveProductsWithCategory() {
        // Given
        Product product1 = new Product();
        product1.setCategory("Electronics");
        product1.setIsActive(true);
        Product product2 = new Product();
        product2.setCategory("Electronics");
        product2.setIsActive(true);
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findByCategory("Electronics")).thenReturn(products);

        // When
        List<Product> result = productService.getAllActiveProducts("Electronics");

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByCategory("Electronics");
    }
}
