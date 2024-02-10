package com.example.demo.erp.person;

import com.example.demo.erp.actionPersonCommon.identifier.Identifier;
import com.example.demo.erp.actionPersonCommon.identifier.IdentifierRepository;
import com.example.demo.erp.actionPersonCommon.identifier.type.IdentifierType;
import com.example.demo.erp.actionPersonCommon.identifier.type.IdentifierTypeRepository;
import com.example.demo.erp.person.archivalPerson.ArchivalPerson;
import com.example.demo.erp.person.archivalPerson.archivalPersonRepository.ArchivalPersonRepository;
import com.example.demo.erp.person.common.Sex;
import com.example.demo.erp.person.common.personsDetails.EmployeeDetails;
import com.example.demo.erp.person.common.personsDetails.EmployeeDetailsRepository;
import com.example.demo.erp.person.person.Person;
import com.example.demo.erp.person.person.personRepository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;


@Component
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ArchivalPersonRepository archivalPersonRepository;
    @Autowired
    private IdentifierTypeRepository identifierTypeRepository;
    @Autowired
    private IdentifierRepository identifierRepository;
    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @PersistenceContext
    EntityManager entityManager;

    public void initializePersons() {
        IdentifierType identifierType = identifierTypeRepository.findByName("PESEL").getFirst();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // The magic is here

        Identifier identifier = Identifier.builder()
                .identifierType(identifierType)
                .identifier("71092958614")
                .build();
        entityManager.persist(identifier);
        entityManager.persist(
                Person.builder()
                        .name("Jan Kowalski")
                        .sex(Sex.M)
                        .identifier(identifier)
                        .realOriginDate(Date.from(LocalDate.of(1971, 9, 29).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .employeeDetails(EmployeeDetails.builder().grossSalary(new BigDecimal("5000")).build())
                        .build()
        );
        ArchivalPerson archivalPerson = new ArchivalPerson(personRepository.findById(1L));
        archivalPersonRepository.save(archivalPerson);

        Person person = new Person();
        BeanUtils.copyProperties(personRepository.findById(1L), person);
//        person.getEmployeeDetails().setIdEmployeeDetails(null);
        person.getEmployeeDetails().setGrossSalary(new BigDecimal("10000"));

        personRepository.saveAndCopy(person);
        archivalPerson = new ArchivalPerson(person);
        archivalPersonRepository.save(archivalPerson);


        identifier = Identifier.builder()
                .identifierType(identifierType)
                .identifier("94071125171")
                .build();
        entityManager.persist(identifier);
        entityManager.persist(
                Person.builder()
                        .name("Adam Nowak")
                        .sex(Sex.M)
                        .identifier(identifier)
                        .realOriginDate(Date.from(LocalDate.of(1994, 7, 11).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .employeeDetails(EmployeeDetails.builder().grossSalary(new BigDecimal("7000")).build())
                        .build()
        );
        archivalPersonRepository.save(new ArchivalPerson(personRepository.findById(2L)));
//        entityManager.persist(new ArchivalPerson(personRepository.findById(2L)));

        identifier = Identifier.builder()
                .identifierType(identifierType)
                .identifier("97051728885")
                .build();
        entityManager.persist(identifier);
        entityManager.persist(
                Person.builder()
                        .name("Marta Żak")
                        .sex(Sex.F)
                        .identifier(identifier)
                        .realOriginDate(Date.from(LocalDate.of(1997, 5, 17).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .employeeDetails(EmployeeDetails.builder().grossSalary(new BigDecimal("6600")).build())
                        .build()
        );
        archivalPersonRepository.save(new ArchivalPerson(personRepository.findById(3L)));

        identifier = Identifier.builder()
                .identifierType(identifierType)
                .identifier("79060717735")
                .build();
        entityManager.persist(identifier);
        entityManager.persist(
                Person.builder()
                        .name("Michał Kubiak")
                        .sex(Sex.M)
                        .identifier(identifier)
                        .realOriginDate(Date.from(LocalDate.of(1979, 6, 7).atStartOfDay(ZoneId.of("UTC")).toInstant()))
                        .employeeDetails(EmployeeDetails.builder().grossSalary(new BigDecimal("4500")).build())
                        .build()
        );
        archivalPersonRepository.save(new ArchivalPerson(personRepository.findById(4L)));
    }

}
