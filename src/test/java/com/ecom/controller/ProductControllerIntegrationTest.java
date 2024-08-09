package com.ecom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetProducts() throws Exception {
        System.out.println("Starting testGetProducts()");

        MvcResult result = mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andReturn();

        // Print the result to see the actual JSON response
        System.out.println("Response Content: " + result.getResponse().getContentAsString());

        // Optionally, print other details from the result if needed
        System.out.println("Response Status: " + result.getResponse().getStatus());
        System.out.println("Response Headers: " + result.getResponse().getHeaderNames());
    }
}
