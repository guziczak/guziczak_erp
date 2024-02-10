package com.example.demo.erp;

import com.example.demo.erp.action.Action;
import com.example.demo.erp.action.ActionType;
import com.example.demo.erp.action.document.DocumentTypeRepository;
import com.example.demo.erp.person.archivalPerson.archivalPersonRepository.ArchivalPersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class PayrollService {
    @Autowired
    private ArchivalPersonRepository archivalPersonRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @PersistenceContext
    EntityManager entityManager;

    public void initializeData() {
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 2, 3).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 2, 15).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Jan Kowalski").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 3, 1).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 3, 20).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Jan Kowalski").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 1, 10).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 1, 19).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Adam Nowak").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 2, 15).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 2, 20).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Marta Żak").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 5, 1).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 5, 20).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Marta Żak").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 9, 7).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 10, 10).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Marta Żak").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 5, 16).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 5, 20).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Jan Kowalski").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 10, 25).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 11, 10).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Jan Kowalski").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 3, 23).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 3, 30).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Michał Kubiak").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .startDate(Date.from(LocalDate.of(2023, 6, 1).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .endDate(Date.from(LocalDate.of(2023, 6, 7).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .archivalPerson(archivalPersonRepository.findActualArchivalPersonByPersonName("Michał Kubiak").orElseThrow())
                        .documentType(documentTypeRepository.findById(5L))
                        .actionType(ActionType.B2B)
                        .build()
        );
    }
}
