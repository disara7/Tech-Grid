package com.ecom.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ClassPathResource;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MultipartFile image;

    @Test
    public void testSaveProduct() {
        MockitoAnnotations.openMocks(this);

        Product product = new Product();
        product.setId(1);
        product.setTitle("Product A");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertEquals("Product A", savedProduct.getTitle());
    }

    @Test
    public void testGetAllProducts() {
        MockitoAnnotations.openMocks(this);

        Product product1 = new Product();
        product1.setId(1);

        Product product2 = new Product();
        product2.setId(2);

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testDeleteProduct() {
        MockitoAnnotations.openMocks(this);

        Product product = new Product();
        product.setId(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        Boolean result = productService.deleteProduct(1);

        assertTrue(result);
        verify(productRepository).delete(product);
    }

    @Test
    public void testDeleteProductNotFound() {
        MockitoAnnotations.openMocks(this);

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Boolean result = productService.deleteProduct(1);

        assertFalse(result);
    }

    @Test
    public void testUpdateProduct() throws IOException {
        MockitoAnnotations.openMocks(this);

        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setImage("oldImage.png");

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setTitle("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setCategory("Updated Category");
        updatedProduct.setPrice(100.0);
        updatedProduct.setStock(10);
        updatedProduct.setImage("newImage.png");
        updatedProduct.setIsActive(true);
        updatedProduct.setDiscount(10.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(image.getOriginalFilename()).thenReturn("newImage.png");
        when(image.isEmpty()).thenReturn(false);
        when(image.getInputStream()).thenReturn(new FileInputStream(new File("src/test/resources/testImage.png")));

        Product result = productService.updateProduct(updatedProduct, image);

        assertNotNull(result);
        assertEquals("Updated Product", result.getTitle());

        // Validate if the file was copied to the correct location
        File savedFile = new ClassPathResource("static/img/product_img/newImage.png").getFile();
        assertTrue(savedFile.exists());
        Files.deleteIfExists(Paths.get(savedFile.getAbsolutePath()));
    }

    @Test
    public void testGetAllActiveProductsNoCategory() {
        MockitoAnnotations.openMocks(this);

        Product activeProduct1 = new Product();
        activeProduct1.setIsActive(true);

        Product activeProduct2 = new Product();
        activeProduct2.setIsActive(true);

        List<Product> products = Arrays.asList(activeProduct1, activeProduct2);

        when(productRepository.findByIsActiveTrue()).thenReturn(products);

        List<Product> result = productService.getAllActiveProducts(null);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllActiveProductsWithCategory() {
        MockitoAnnotations.openMocks(this);

        Product activeProduct = new Product();
        activeProduct.setIsActive(true);
        activeProduct.setCategory("Electronics");

        List<Product> products = Arrays.asList(activeProduct);

        when(productRepository.findByCategory("Electronics")).thenReturn(products);

        List<Product> result = productService.getAllActiveProducts("Electronics");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getCategory());
    }
}
