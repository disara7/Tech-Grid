package com.ecom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        // Verify that the context loads successfully
    }

    @Test
    public void testGetProducts() throws Exception {
        // Perform a GET request to the /api/products endpoint
        ResultActions result = mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON));

        // Verify the response status and content
        result.andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(jsonPath("$", hasSize(greaterThan(0)))); // Check if the response has at least one product
    }

    @Test
    public void testGetProductById() throws Exception {
        // Perform a GET request to the /api/products/{id} endpoint with a valid ID
        ResultActions result = mockMvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Verify the response status and content
        result.andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(jsonPath("$.id", is(1))) // Verify the ID in the response
              .andExpect(jsonPath("$.name", is(not(emptyOrNullString())))); // Verify that the name is not empty
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        // Perform a GET request to the /api/products/{id} endpoint with an invalid ID
        ResultActions result = mockMvc.perform(get("/api/products/999")
                .contentType(MediaType.APPLICATION_JSON));

        // Verify the response status is 404 Not Found
        result.andExpect(status().isNotFound());
    }
}
