package com.example.demo.erp.person.common.personsDetails;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeDetailsRepository extends CrudRepository<EmployeeDetails, Long> {
    EmployeeDetails findById(long id);
}