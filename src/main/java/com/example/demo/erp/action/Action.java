package com.example.demo.erp.action;

import com.example.demo.erp.actionPersonCommon.identifier.dates.DateEntity;
import com.example.demo.erp.action.document.DocumentType;
import com.example.demo.erp.person.archivalPerson.ArchivalPerson;
import lombok.*;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAction;
    private Date creationDate;
    private Date startDate;
    private Date endDate;
    @OneToMany
    private List<DateEntity> dates;
    private Integer symbolNumber;
    private String symbol;
    @ManyToOne
    @JoinColumn(name = "idArchivalPerson")
    private ArchivalPerson archivalPerson;
    @ManyToOne
    @JoinColumn(name = "idDocumentType")
    private DocumentType documentType;
    private ActionType actionType;

}
