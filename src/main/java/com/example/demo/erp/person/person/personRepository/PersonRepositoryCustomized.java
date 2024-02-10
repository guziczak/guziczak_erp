package com.example.demo.erp.person.person.personRepository;

public interface PersonRepositoryCustomized<T, ID> {
    <S extends T> S save(S entity);
    <S extends T> S saveAndCopy(S entity);


}