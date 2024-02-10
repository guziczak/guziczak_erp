package com.example.demo.erp.person;

import com.example.demo.erp.person.common.personsDetails.Salary;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class AbstractEmployee {

    private BigDecimal grossSalary;

//    private List<Salary> salariesList;

}