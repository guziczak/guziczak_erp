package com.example.demo.erp.person.person.personRepository;

import com.example.demo.erp.person.person.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository
        extends CrudRepository<Person, Long>, PersonRepositoryCustomized<Person, Long> {
    Person findById(long id);

}