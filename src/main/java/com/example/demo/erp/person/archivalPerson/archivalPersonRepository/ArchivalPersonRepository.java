package com.example.demo.erp.person.archivalPerson.archivalPersonRepository;

import com.example.demo.erp.person.archivalPerson.ArchivalPerson;
import org.springframework.data.repository.CrudRepository;

public interface ArchivalPersonRepository
        extends CrudRepository<ArchivalPerson, Long>, ArchivalPersonRepositoryCustomized<ArchivalPerson, Long> {
    ArchivalPerson findById(long id);

}