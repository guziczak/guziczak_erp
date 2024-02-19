package com.example.demo.erp;

import com.example.demo.erp.person.AbstractEmployee;
import com.example.demo.erp.person.common.personsDetails.EmployeeDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Deprecated
public class ArchivalEmployee extends AbstractEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArchivalEmployee;

    public ArchivalEmployee(EmployeeDetails employeeDetails) {
        BeanUtils.copyProperties(employeeDetails, this);
    }

}