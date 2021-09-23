package com.github.alextheartisan.boox.documents;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {
    Page<Document> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
