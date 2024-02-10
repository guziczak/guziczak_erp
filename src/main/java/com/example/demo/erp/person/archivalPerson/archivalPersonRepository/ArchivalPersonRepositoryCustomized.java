package com.example.demo.erp.person.archivalPerson.archivalPersonRepository;

import com.example.demo.erp.person.person.Person;

import java.util.Optional;

public interface ArchivalPersonRepositoryCustomized<T, ID> {
    <S extends T> S save(S entity);
    Optional<T> findActualArchivalPersonByPerson(Person person);
    Optional<T> findActualArchivalPersonByPersonName(String personName);

}