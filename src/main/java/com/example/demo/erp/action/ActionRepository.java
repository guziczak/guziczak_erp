package com.example.demo.erp.action;

import org.springframework.data.repository.CrudRepository;

public interface ActionRepository extends CrudRepository<Action, Long> {
    Action findById(long id);
}