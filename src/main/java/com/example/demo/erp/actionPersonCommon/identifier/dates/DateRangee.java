package com.example.demo.erp.actionPersonCommon.identifier.dates;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DateRangee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDateRangee;
    private Date from;
    private Date to;

}
