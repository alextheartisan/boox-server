package com.github.alextheartisan.boox.documents;

import static org.springframework.data.domain.Sort.DEFAULT_DIRECTION;
import static org.springframework.http.MediaType.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO:
 *   - Bulk Creation
 *   - Bulk Deletion
 */
@Tag(name = "Document", description = "Manipulating documents (Uploading/Downloading)")
@RestController
@RequestMapping(value = "/documents", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class DocumentController {

    private final DocumentService service;

    @Operation(
        summary = "Get all documents",
        description = "Return a list of documents, paginated & filtered",
        parameters = {
            @Parameter(name = "page"), @Parameter(name = "size"), @Parameter(name = "search"),
        }
    )
    @GetMapping
    ResponseEntity<Map<String, Object>> getAllDocuments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "ASC") Sort.Direction direction,
        @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok().body(service.getAllDocuments(search, page, size));
    }

    @Operation(summary = "Get a document")
    @GetMapping("/{id}")
    ResponseEntity<?> getDocument(@PathVariable Long id) {
        return ResponseEntity.of(service.getDocument(id));
    }

    @Operation(summary = "Create a document")
    @PostMapping
    ResponseEntity<Document> createDocument(@Valid @RequestBody Document document) {
        var savedDocument = service.createDocument(document);

        URI location = URI.create(String.format("/documents/%s", savedDocument.getId()));

        return ResponseEntity.created(location).body(savedDocument);
    }

    @Operation(summary = "Update a document")
    @PutMapping("/{id}")
    ResponseEntity<?> updateDocument(@PathVariable Long id, @Valid @RequestBody Document document) {
        return ResponseEntity.of(service.editDocument(id, document));
    }

    @Operation(summary = "Delete a document")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteDocument(@PathVariable Long id) throws IOException {
        var deleted = service.deleteDocument(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get document annotations")
    @GetMapping("/{id}/annotations")
    String getDocumentAnnotations(@PathVariable Long id) {
        return "Endpoint is not implemented yet";
    }

    @Operation(summary = "Upload a document")
    @PostMapping(value = "{id}/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> uploadDocument(@PathVariable Long id, @RequestParam MultipartFile file)
        throws IOException {
        var saved = service.uploadDocument(id, file);

        if (!saved) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict");
        }

        return ResponseEntity.ok("Success");
    }

    @Operation(summary = "Download a document")
    @PostMapping("/{id}/download")
    String downloadDocument(@PathVariable Long id) {
        return "Endpoint is not implemented yet";
    }
}
