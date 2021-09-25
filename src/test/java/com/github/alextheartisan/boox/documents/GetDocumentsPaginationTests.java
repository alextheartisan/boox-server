package com.github.alextheartisan.boox.documents;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GetDocumentsPaginationTests {

    @Autowired
    private MockMvc spring;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DocumentRepository repository;

    void insertDocuments() {
        repository.saveAll(
            List.of(
                new Document("Dive into Python", "Mark Pilgrim"),
                new Document("Effective Java", "Joshua Bloch"),
                new Document("JavaScript: The Good Parts", "David Flanagan")
            )
        );
    }

    @Test
    @DisplayName("should [paginate documents as page=0 & size=10] when [no params]")
    void getDocuments_noParams() throws Exception {
        insertDocuments();

        spring
            .perform(get("/documents"))
            .andExpect(jsonPath("$.data[0].title", is("Dive into Python")))
            .andExpect(jsonPath("$.data[1].title", is("Effective Java")));
    }

    @Test
    @DisplayName("should [paginate documents]")
    void getDocuments_paginationParams() throws Exception {
        insertDocuments();

        spring
            .perform(get("/documents?page=0&size=1"))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].title", is("Dive into Python")));
        spring
            .perform(get("/documents?page=1&size=1"))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].title", is("Effective Java")));
        spring
            .perform(get("/documents?page=2&size=1"))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].title", is("JavaScript: The Good Parts")));
        spring.perform(get("/documents?page=3&size=1")).andExpect(jsonPath("$.data", hasSize(0)));
    }
}
