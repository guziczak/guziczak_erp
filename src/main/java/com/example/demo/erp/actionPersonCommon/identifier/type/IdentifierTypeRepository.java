package com.example.demo.erp.actionPersonCommon.identifier.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentifierTypeRepository extends JpaRepository<IdentifierType, Long> {
    List<IdentifierType> findByName(String name);

}