package com.example.demo.erp.actionPersonCommon.identifier;

import com.example.demo.erp.actionPersonCommon.identifier.type.IdentifierType;
import lombok.*;

import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Identifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIdentifier;
    @ManyToOne
    @JoinColumn(name = "idIdentifierType")
    private IdentifierType identifierType;
    private String identifier;

}
