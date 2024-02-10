package com.example.demo.erp.action.document;

import org.springframework.data.repository.CrudRepository;

public interface DocumentTypeRepository extends CrudRepository<DocumentType, Long> {
    DocumentType findById(long id);
}