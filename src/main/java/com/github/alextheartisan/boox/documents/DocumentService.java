package com.github.alextheartisan.boox.documents;

import static java.util.Map.entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
class DocumentService {

    private final Path root = Paths.get("library");

    private final DocumentRepository repository;

    @PostConstruct
    void init() throws IOException {
        Files.createDirectories(root);
    }

    Map<String, Object> getAllDocuments(String search, int page, int size) {
        var pageable = PageRequest.of(page, size);
        var documents = search != null
            ? repository.findByTitleContainingIgnoreCase(search, pageable)
            : repository.findAll(pageable);

        return new HashMap<>(
            Map.ofEntries(
                entry("data", documents.getContent()),
                entry("totalElements", documents.getTotalElements()),
                entry("totalPages", documents.getTotalPages()),
                entry("currentPage", documents.getNumber())
            )
        );
    }

    Optional<Map<String, Object>> getDocument(Long id) {
        return repository.findById(id).map(val -> Map.of("data", val));
    }

    Document createDocument(Document document) {
        return repository.save(document);
    }

    Optional<?> editDocument(Long id, Document document) {
        return repository
            .findById(id)
            .map(d -> {
                d.setTitle(document.getTitle());
                d.setAuthor(document.getAuthor());

                return Map.of("data", repository.save(d));
            });
    }

    boolean deleteDocument(Long id) throws IOException {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);

        Files.deleteIfExists(root.resolve(id + ".pdf"));

        return true;
    }

    void deleteAllDocuments(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    boolean uploadDocument(Long id, MultipartFile file) throws IOException {
        var path = root.resolve(id + ".pdf");

        if (Files.exists(path)) {
            return false;
        }

        Files.copy(file.getInputStream(), path);

        return true;
    }
}
