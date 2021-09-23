package com.github.alextheartisan.boox.documents;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class DocumentService {
    /*
    @Autowired
    private DocumentRepository repository;

    private final List<Document> documents = new ArrayList<>();

    {
        documents.add(new Document(1L, "Dive into Python", "Dirk"));
        documents.add(new Document(2L, "Effective Java", "Joshua"));
        documents.add(new Document(3L, "JavaScript: The best parts", "David"));
    }

    public List<Document> getAll(String search) {
        return repository.findByTitleContaining(search);
        //        return documents
        //            .stream()
        //            .filter(d -> containsIgnoreCase(d.getTitle(), search))
        //            .collect(toList());
    }


    public void save(Document document) {
        //        documents.add(document);
    }

    public Document get(Long id) {
        //        return documents
        //            .stream()
        //            .filter(d -> d.getId().equals(id))
        //            .findAny()
        //            .orElse(null);
    }
    */

}
