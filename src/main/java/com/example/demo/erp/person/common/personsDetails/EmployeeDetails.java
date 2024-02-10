package com.example.demo.erp.person.common.personsDetails;

import com.example.demo.erp.person.AbstractEmployee;

import org.springframework.beans.BeanUtils;
import lombok.*;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDetails extends AbstractEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long idEmployeeDetails;

    public EmployeeDetails(EmployeeDetails employeeDetails) {
        BeanUtils.copyProperties(employeeDetails, this);
    }
    public EmployeeDetails(EmployeeDetails employeeDetails,int nothing) {
        this.setGrossSalary(employeeDetails.getGrossSalary());
    }

//    @Deprecated
//    public EmployeeDetails(EmployeeDetails employeeDetails) {
////        super(employeeDetails.getGrossSalary());
////        this.idEmployeeDetails = employeeDetails.getIdEmployeeDetails();
//        super(employeeDetails != null ? employeeDetails.getGrossSalary() : null);
//        this.idEmployeeDetails = employeeDetails != null ? employeeDetails.getIdEmployeeDetails() : null;
//    }

    @Builder
    public EmployeeDetails(BigDecimal grossSalary, Long idEmployeeDetails) {
        super(grossSalary);
        this.idEmployeeDetails = idEmployeeDetails;
    }

}