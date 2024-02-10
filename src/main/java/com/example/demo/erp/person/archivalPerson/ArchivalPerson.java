package com.example.demo.erp.person.archivalPerson;

import com.example.demo.erp.person.common.personsDetails.EmployeeDetails;
import com.example.demo.erp.actionPersonCommon.identifier.Identifier;
import com.example.demo.erp.person.AbstractPerson;
import com.example.demo.erp.person.person.Person;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class ArchivalPerson extends AbstractPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArchivalPerson;
    private Long idPerson;

    private Date recordOriginDate;
    @ManyToOne
    @JoinColumn(name = "idIdentifier")
    private Identifier identifier;
    @Nullable
    @ManyToOne
    @JoinColumn(name = "idEmployeeDetails")
    private EmployeeDetails employeeDetails;

    public ArchivalPerson(Person person) {
        BeanUtils.copyProperties(person, this);
//        this.employeeDetails = person.getEmployeeDetails()!=null ? new EmployeeDetails(person.getEmployeeDetails()) : null;
        this.recordOriginDate = new Date();
    }

}
