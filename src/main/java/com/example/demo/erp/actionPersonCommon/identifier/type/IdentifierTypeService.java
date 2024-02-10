package com.example.demo.erp.actionPersonCommon.identifier.type;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class IdentifierTypeService {
    @PersistenceContext
    EntityManager entityManager;

    public void initializeIdentifiers() {
        entityManager.persist(new IdentifierType(null, "PESEL"));
        entityManager.persist(new IdentifierType(null, "NIP"));
    }

}
