package com.github.alextheartisan.boox.documents;

import static java.util.Map.entry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/documents")
@RequiredArgsConstructor
class DocumentController {

    private final DocumentRepository repository;

    @Operation(
        summary = "Get all documents",
        description = "Return a list of documents, paginated & filtered",
        parameters = {
            @Parameter(name = "page"), @Parameter(name = "size"), @Parameter(name = "search"),
        }
    )
    @GetMapping(produces = "application/json")
    ResponseEntity<Map<String, Object>> getAllDocuments(
        @RequestParam(required = false) String search,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var documents = search != null
            ? repository.findByTitleContainingIgnoreCase(search, pageable)
            : repository.findAll(pageable);
        var body = Map.ofEntries(
            entry("data", documents.getContent()),
            entry("totalElements", documents.getTotalElements()),
            entry("totalPages", documents.getTotalPages()),
            entry("currentPage", documents.getNumber())
        );

        return ResponseEntity.ok().body(body);
    }

    @Operation(summary = "Get a document")
    @GetMapping("/{id}")
    ResponseEntity<?> getDocument(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id).map(val -> Map.of("data", val)));
    }

    @Operation(summary = "Create a document")
    @PostMapping
    ResponseEntity<Document> createDocument(@Valid @RequestBody Document document) {
        var savedDocument = repository.save(document);

        URI location = URI.create(String.format("/documents/%s", savedDocument.getId()));

        return ResponseEntity.created(location).body(savedDocument);
    }

    @Operation(summary = "Edit a document")
    @PutMapping("/{id}")
    ResponseEntity<?> updateDocument(@PathVariable Long id, @Valid @RequestBody Document dto) {
        return ResponseEntity.of(
            repository
                .findById(id)
                .map(d -> {
                    d.setTitle(dto.getTitle());
                    d.setAuthor(dto.getAuthor());

                    return Map.of("data", repository.save(d));
                })
        );
    }

    @Operation(summary = "Delete a document")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteDocument(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Download a document")
    @PostMapping("/{id}/download")
    String downloadDocument(@PathVariable Long id) {
        return "Endpoint is not implemented yet";
    }

    @Operation(summary = "Upload a document")
    @PostMapping("/upload")
    String uploadDocument(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());

        System.out.println(file.getName());

        System.out.println(file.getSize());

        return "file";
    }

    @Operation(summary = "Get document annotations")
    @GetMapping("/{id}/annotations")
    String getDocumentAnnotations(@PathVariable Long id) {
        return "Endpoint is not implemented yet";
    }
}
