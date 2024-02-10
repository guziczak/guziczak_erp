package com.example.demo.erp.person.person;

import com.example.demo.erp.person.common.personsDetails.EmployeeDetails;
import com.example.demo.erp.actionPersonCommon.identifier.Identifier;
import com.example.demo.erp.person.common.Sex;
import com.example.demo.erp.person.AbstractPerson;

import lombok.*;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person extends AbstractPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long idPerson;
    @ManyToOne
    @JoinColumn(name = "idIdentifier")
    private Identifier identifier;
    @Nullable
    @ManyToOne
    @JoinColumn(name = "idEmployeeDetails")
    private EmployeeDetails employeeDetails;

    @Builder
    public Person(String name, String city, Long idPerson, Identifier identifier, Date realOriginDate, Sex sex, EmployeeDetails employeeDetails) {
        super(name, city, realOriginDate, sex);
        this.idPerson = idPerson;
        this.identifier = identifier;
        this.employeeDetails = employeeDetails;
    }

}