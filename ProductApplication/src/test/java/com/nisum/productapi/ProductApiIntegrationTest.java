package com.nisum.productapi;



import com.nisum.productapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddAndGetProduct() throws Exception {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Desc");
        product.setPrice(99.99);
        product.setStockQuantity(10);
        product.setCategory("Test");

        String productJson = objectMapper.writeValueAsString(product);

        // POST
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        // GET All
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testUpdateAndDeleteProduct() throws Exception {
        Product product = new Product();
        product.setName("To Update");
        product.setDescription("Before Update");
        product.setPrice(10.0);
        product.setStockQuantity(5);
        product.setCategory("Misc");

        String productJson = objectMapper.writeValueAsString(product);

        // Create
        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andReturn().getResponse().getContentAsString();

        Product saved = objectMapper.readValue(response, Product.class);

        // Update
        saved.setDescription("Updated Desc");
        String updatedJson = objectMapper.writeValueAsString(saved);

        mockMvc.perform(put("/products/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Desc"));

        // Delete
        mockMvc.perform(delete("/products/" + saved.getId()))
                .andExpect(status().isOk());
    }
}

