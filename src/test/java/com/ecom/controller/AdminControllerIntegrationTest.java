package com.ecom.controller;

import com.ecom.model.Product;
import com.ecom.service.ProductService;
import com.ecom.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mockito.Mockito;

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
        System.out.println("Starting testSaveProduct...");

        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setStock(10);
        product.setCategory("Electronics");
        product.setIsActive(true);
        product.setDiscount(5);

        System.out.println("Product Details: " + product);

        MockMultipartFile image = new MockMultipartFile("file", "testImage.png", "image/png", "image content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/saveProduct/save")
            .file(image)
            .param("title", product.getTitle())
            .param("description", product.getDescription())
            .param("price", String.valueOf(product.getPrice()))
            .param("stock", String.valueOf(product.getStock()))
            .param("category", product.getCategory())
            .param("isActive", String.valueOf(product.getIsActive()))
            .param("discount", String.valueOf(product.getDiscount())))
            .andDo(result -> {
                System.out.println("Response Status: " + result.getResponse().getStatus());
                System.out.println("Response Headers: " + result.getResponse().getHeaderNames());
                System.out.println("Response Body: " + result.getResponse().getContentAsString());
            })
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/loadAddProduct"));

        System.out.println("Finished testSaveProduct.");
    }

    @Test
    public void testSaveProductWithEmptyData() throws Exception {
        MockMultipartFile image = new MockMultipartFile("file", "testImage.png", "image/png", "image content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/saveProduct/save")
            .file(image)
            .param("title", "")
            .param("description", "")
            .param("price", "0")
            .param("stock", "0")
            .param("category", "")
            .param("isActive", "false")
            .param("discount", "0"))
            .andDo(result -> {
                System.out.println("Response Status: " + result.getResponse().getStatus());
                System.out.println("Response Body: " + result.getResponse().getContentAsString());
            })
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/loadAddProduct")); // Adjust based on your error handling
    }

    


    @Test
public void testSaveProductWithMissingFile() throws Exception {
    Product product = new Product();
    product.setTitle("Test Product");
    product.setDescription("Test Description");
    product.setPrice(100.0);
    product.setStock(10);
    product.setCategory("Electronics");
    product.setIsActive(true);
    product.setDiscount(5);

    mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/saveProduct/save")
        .param("title", product.getTitle())
        .param("description", product.getDescription())
        .param("price", String.valueOf(product.getPrice()))
        .param("stock", String.valueOf(product.getStock()))
        .param("category", product.getCategory())
        .param("isActive", String.valueOf(product.getIsActive()))
        .param("discount", String.valueOf(product.getDiscount())))
        .andDo(result -> {
            System.out.println("Response Status: " + result.getResponse().getStatus());
            System.out.println("Response Body: " + result.getResponse().getContentAsString());
        })
        .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Adjust based on your actual status code
}


    @Test
    public void testSaveProductSuccess() throws Exception {
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setStock(10);
        product.setCategory("Electronics");
        product.setIsActive(true);
        product.setDiscount(5);

        MockMultipartFile image = new MockMultipartFile("file", "testImage.png", "image/png", "image content".getBytes());

        Mockito.when(productService.saveProduct(Mockito.any(Product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/saveProduct/save")
            .file(image)
            .param("title", product.getTitle())
            .param("description", product.getDescription())
            .param("price", String.valueOf(product.getPrice()))
            .param("stock", String.valueOf(product.getStock()))
            .param("category", product.getCategory())
            .param("isActive", String.valueOf(product.getIsActive()))
            .param("discount", String.valueOf(product.getDiscount())))
            .andDo(result -> {
                System.out.println("Response Status: " + result.getResponse().getStatus());
                System.out.println("Response Body: " + result.getResponse().getContentAsString());
            })
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/loadAddProduct"));
    }

    @Test
    public void testSaveProductWithError() throws Exception {
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setStock(10);
        product.setCategory("Electronics");
        product.setIsActive(true);
        product.setDiscount(5);

        MockMultipartFile image = new MockMultipartFile("file", "testImage.png", "image/png", "image content".getBytes());

        Mockito.when(productService.saveProduct(Mockito.any(Product.class))).thenReturn(null); // Simulate save failure

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/saveProduct/save")
            .file(image)
            .param("title", product.getTitle())
            .param("description", product.getDescription())
            .param("price", String.valueOf(product.getPrice()))
            .param("stock", String.valueOf(product.getStock()))
            .param("category", product.getCategory())
            .param("isActive", String.valueOf(product.getIsActive()))
            .param("discount", String.valueOf(product.getDiscount())))
            .andDo(result -> {
                System.out.println("Response Status: " + result.getResponse().getStatus());
                System.out.println("Response Body: " + result.getResponse().getContentAsString());
            })
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/loadAddProduct")); // Adjust based on your error handling
    }
}
