package com.example.demo.erp.person;

import com.example.demo.erp.person.common.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class AbstractPerson {
    private String name;
    private String city;
    private Date realOriginDate;
    private Sex sex;

}