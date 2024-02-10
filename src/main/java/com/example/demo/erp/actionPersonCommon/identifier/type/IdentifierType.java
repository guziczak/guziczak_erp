package com.example.demo.erp.actionPersonCommon.identifier.type;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IdentifierType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIdentifierType;
    private String name;


}
