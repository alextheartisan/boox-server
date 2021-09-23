package com.github.alextheartisan.boox.documents;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.List;
import javax.print.Doc;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DocumentRepository repo;

    @BeforeEach
    void clear() {
        repo.deleteAll();
    }

    @Nested
    class DeleteDocument {

        @Test
        @DisplayName("should delete a document")
        void deleteDocument() throws Exception {
            var document = new Document("New Book", "Cool guy");
            var result = mvc
                .perform(
                    post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(document))
                )
                .andReturn();

            var id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

            mvc.perform(delete("/documents/" + id)).andExpect(status().isNoContent());

            mvc
                .perform(get("/documents").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", is(empty())));
        }
    }

    @Nested
    class UpdateDocument {

        @Test
        @DisplayName("should update a document")
        void updateDocument() throws Exception {
            var document = new Document("First Book", "Some guy");
            var result = mvc
                .perform(
                    post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(document))
                )
                .andReturn();

            var id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

            var updatedDocument = new Document("Second Book", "Johnny");
            mvc
                .perform(
                    put("/documents/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedDocument))
                )
                .andExpect(jsonPath("$.data.id", is(id)))
                .andExpect(jsonPath("$.data.title", is(updatedDocument.getTitle())))
                .andExpect(jsonPath("$.data.author", is(updatedDocument.getAuthor())));
        }
    }

    @Nested
    class CreateDocument {

        @Test
        @DisplayName("should [create new document]")
        void createDocument() throws Exception {
            var document = new Document("New Book", "Cool guy");
            var result = mvc
                .perform(
                    post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(document))
                )
                .andExpect(status().isCreated())
                .andReturn();

            var id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

            mvc
                .perform(get("/documents/" + id))
                .andExpect(jsonPath("$.data.id", is(id)))
                .andExpect(jsonPath("$.data.title", is(document.getTitle())))
                .andExpect(jsonPath("$.data.author", is(document.getAuthor())));
        }
    }

    @Nested
    class GetDocument {

        @Test
        @DisplayName("should [return a document] when [it exists]")
        void getDocument_existing() throws Exception {
            var document = repo.save(new Document("Dive into Python", "Mark Pilgrim"));

            mvc
                .perform(
                    get("/documents/" + document.getId()).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", is("Dive into Python")))
                .andExpect(jsonPath("$.data.author", is("Mark Pilgrim")));
        }

        @Test
        @DisplayName("should [return an error] when [document not found]")
        void getDocument_notFound() throws Exception {
            mvc
                .perform(get("/documents/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        }
    }

    @Nested
    class GetAllDocuments {

        @Test
        @DisplayName(
            "should [return response with empty/zero values] when [no documents in database]"
        )
        void getAllDocuments_empty() throws Exception {
            mvc
                .perform(get("/documents").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", is(empty())))
                .andExpect(jsonPath("$.totalPages").value(0))
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.currentPage").value(0));
        }

        @Test
        @DisplayName("should [return a fulfilled response] when [has documents in database]")
        void getAllDocuments_present() throws Exception {
            repo.save(new Document("Dive into Python", "Mark Pilgrim"));

            mvc
                .perform(get("/documents").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].title", is("Dive into Python")))
                .andExpect(jsonPath("$.data[0].author", is("Mark Pilgrim")))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.currentPage").value(0));
        }
    }
}
