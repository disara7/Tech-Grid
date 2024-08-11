package com.ecom.controller;

import com.ecom.model.Product;
import com.ecom.service.ProductService;
import com.ecom.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.mock.web.MockMultipartFile;

@WebMvcTest(AdminController.class)
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testSaveProduct() throws Exception {
        // Print a message indicating the start of the test
        System.out.println("Starting testSaveProduct...");

        // Create a sample product
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setStock(10);
        product.setCategory("Electronics");
        product.setIsActive(true);
        product.setDiscount(5);

        // Print the product details
        System.out.println("Product Details: " + product);

        // Create a mock multipart file
        MockMultipartFile image = new MockMultipartFile("file", "testImage.png", "image/png", "image content".getBytes());

        // Perform the request and print debug information
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/saveProduct")
                .file(image)
                .param("title", product.getTitle())
                .param("description", product.getDescription())
                .param("price", String.valueOf(product.getPrice()))
                .param("stock", String.valueOf(product.getStock()))
                .param("category", product.getCategory())
                .param("isActive", String.valueOf(product.getIsActive()))
                .param("discount", String.valueOf(product.getDiscount())))
                .andDo(result -> {
                    // Print the response status and headers
                    System.out.println("Response Status: " + result.getResponse().getStatus());
                    System.out.println("Response Headers: " + result.getResponse().getHeaderNames());
                })
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/loadAddProduct"));

        // Print a message indicating the end of the test
        System.out.println("Finished testSaveProduct.");
    }
}
