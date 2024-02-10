package com.example.demo.erp;

import com.example.demo.erp.action.Action;
import com.example.demo.erp.action.ActionRepository;
import com.example.demo.erp.action.ActionType;
import com.example.demo.erp.action.document.DocumentType;
import com.example.demo.erp.action.document.DocumentTypeRepository;
import com.example.demo.erp.actionPersonCommon.identifier.type.IdentifierTypeService;
import com.example.demo.erp.person.archivalPerson.ArchivalPerson;
import com.example.demo.erp.person.PersonService;
import com.example.demo.erp.person.archivalPerson.archivalPersonRepository.ArchivalPersonRepository;
import com.example.demo.erp.examples.example.Tag;
import com.example.demo.erp.examples.example.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class TestController {

    @Autowired
    private ArchivalPersonRepository archivalPersonRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private PayrollService payrollService;
    @Autowired
    private IdentifierTypeService identifierTypeService;
    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping(value = "/initialize/all")
    @GetMapping
    @Transactional
    public ArchivalPerson initializeAll() {
        initializeTriggers();
        identifierTypeService.initializeIdentifiers();
        personService.initializePersons();
        initializeDocumentTypes();
        payrollService.initializeData();

        Tag tag = new Tag();
        entityManager.persist(tag);

        Tutorial tutorial = new Tutorial();
        tutorial.setTags(new ArrayList<>(List.of(tag)));
        entityManager.persist(tutorial);

        tutorial.removeTag(1);
        entityManager.merge(tutorial);

//        return StreamSupport.stream(actionRepository.findAll().spliterator(), false)
//                .collect(Collectors.toList());
        return archivalPersonRepository.findById(1L);
    }

    @RequestMapping(value = "/initialize/triggers")
    @GetMapping
    @Transactional
    public void initializeTriggers() {
        entityManager.createNativeQuery("drop trigger if exists generate_actions_symbol_trg on \"action\"").executeUpdate();
        entityManager.createNativeQuery("drop function if exists generate_actions_symbol()").executeUpdate();

        entityManager.createNativeQuery("""
                        create function generate_actions_symbol()
                        returns trigger
                        language 'plpgsql'
                        as $$
                        begin
                        	new.symbol_number \\:= (
                        		select 1 as "symbol_number"
                        		union
                        		(
                        			select a.symbol_number + 1 as "symbol_number" from "action" a
                        			join document_type dt on dt.id_document_type = a.id_document_type
                        			where
                        			dt.id_document_type = new.id_document_type
                        			and EXTRACT(YEAR FROM a.creation_date) = EXTRACT(YEAR FROM new.creation_date)
                        			and a.symbol_number is not null
                        			order by a.symbol_number desc
                        			limit 1
                        		)
                        		order by "symbol_number" desc
                        		limit 1
                        	);
                        	new.symbol \\:= concat(
                        	    (select dt2.prefix from document_type dt2 where dt2.id_document_type = new.id_document_type),
                                (CASE
                                    WHEN new.symbol_number<=9 THEN concat(' 00000')
                                    WHEN new.symbol_number<=99 THEN concat(' 0000')
                                    WHEN new.symbol_number<=999 THEN concat(' 000')
                                    WHEN new.symbol_number<=9999 THEN concat(' 00')
                                    WHEN new.symbol_number<=99999 THEN concat(' 0')
                                    ELSE concat(' ',new.symbol_number)
                                END),
                                new.symbol_number,
                                to_char(now(),'/YY')
                        	);
                        	return new;
                        end;
                        $$;
                """).executeUpdate();

        entityManager.createNativeQuery("""
                create trigger generate_actions_symbol_trg
                before insert
                on "action"
                for each row
                execute procedure generate_actions_symbol()
                """).executeUpdate();

        entityManager.createNativeQuery("drop trigger if exists copy_person_trg on \"person\"").executeUpdate();
        entityManager.createNativeQuery("drop function if exists copy_person()").executeUpdate();

//        entityManager.createNativeQuery("""
//                create function copy_person()
//                returns trigger
//                language 'plpgsql'
//                as $$
//                begin
//                	insert into archival_person(creation_date,id_person,city,name)
//                	values(now(),new.id_person,new.city,new.name);
//                    return new;
//                end;
//                $$;
//                """).executeUpdate();
//
//        entityManager.createNativeQuery("""
//                create trigger copy_person_trg
//                after insert or update
//                on "person"
//                for each row
//                execute procedure copy_person()
//                """).executeUpdate();
    }

    @RequestMapping(value = "/initialize/document-types")
    @GetMapping
    @Transactional
    public void initializeDocumentTypes() {
        entityManager.persist(new DocumentType(null, "FV", "Faktura VAT"));
        entityManager.persist(new DocumentType(null, "FK", "Faktura korygująca"));
        entityManager.persist(new DocumentType(null, "WZ", "Wydanie zewnętrzne"));
        entityManager.persist(new DocumentType(null, "PZ", "Przyjęcie zewnętrzne"));
        entityManager.persist(new DocumentType(null, "LP", "Lista płac"));
    }

    @RequestMapping(value = "/initialize/action/fv")
    @GetMapping
    @Transactional
    public void initializeActionfv() {
        entityManager.persist(
                Action.builder()
                        .creationDate(new Date())
                        .archivalPerson(archivalPersonRepository.findById(3L))
                        .documentType(documentTypeRepository.findById(1L))
                        .actionType(ActionType.B2B)
                        .build()
        );
    }

    @RequestMapping(value = "/initialize/lista-plac")
    @GetMapping
    @Transactional
    public List<Action> initializeListaPlac() {
        return StreamSupport.stream(actionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/initialize/action/wz")
    @GetMapping
    @Transactional
    public void initializeActionwz() {
        for (int i = 0; i < 100; i++) {
            entityManager.persist(
                    Action.builder()
                            .creationDate(new Date())
                            .archivalPerson(archivalPersonRepository.findById(1L))
                            .documentType(documentTypeRepository.findById(3L))
                            .actionType(ActionType.B2B)
                            .build()
            );
        }
    }

}