package com.nisum.library;



import com.nisum.library.model.Book;
import com.nisum.library.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void testCreateAndGetBook() throws Exception {
        Book book = new Book(null, "Test Book", "Author A", 2020);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Book")));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book saved = repo.save(new Book(null, "Old", "Old Author", 2000));
        saved.setTitle("Updated");

        mockMvc.perform(put("/books/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated")));
    }

    @Test
    void testDeleteBook() throws Exception {
        Book saved = repo.save(new Book(null, "To Delete", "Author", 1999));

        mockMvc.perform(delete("/books/" + saved.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/books/" + saved.getId()))
                .andExpect(status().isNotFound());
    }
}
