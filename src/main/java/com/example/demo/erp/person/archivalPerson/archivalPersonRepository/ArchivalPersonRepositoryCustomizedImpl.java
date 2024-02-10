package com.example.demo.erp.person.archivalPerson.archivalPersonRepository;

import com.example.demo.erp.*;
import com.example.demo.erp.person.archivalPerson.ArchivalPerson;
import com.example.demo.erp.person.common.personsDetails.EmployeeDetails;
import com.example.demo.erp.person.common.personsDetails.EmployeeDetailsRepository;
import com.example.demo.erp.person.person.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ArchivalPersonRepositoryCustomizedImpl<T, ID> implements ArchivalPersonRepositoryCustomized<T, ID> {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        Assert.notNull(entity, "Entity must not be null");
        if (((ArchivalPerson) entity).getEmployeeDetails() != null) {
            if (((ArchivalPerson) entity).getEmployeeDetails().getIdEmployeeDetails() == null) {
                this.entityManager.persist(((ArchivalPerson) entity).getEmployeeDetails());
            } else {
                EmployeeDetails employeeDetails =
                        employeeDetailsRepository.findById(((ArchivalPerson) entity).getEmployeeDetails().getIdEmployeeDetails())
                                .orElseThrow();

                boolean isChanged = false;
                Map<String, PreviousNextDTO> map = Collections.emptyMap();
                try {
                    isChanged = this.isChanged(employeeDetails, ((ArchivalPerson) entity).getEmployeeDetails());
                    map = getMapOfChanges(employeeDetails, ((ArchivalPerson) entity).getEmployeeDetails());
                } catch (Exception ignored) {
                }

                if (isChanged) {
                    ((ArchivalPerson) entity).getEmployeeDetails().setIdEmployeeDetails(null);
                    this.entityManager.persist(((ArchivalPerson) entity).getEmployeeDetails());
                }
            }
        }
        if (((ArchivalPerson) entity).getIdArchivalPerson() == null) {
            this.entityManager.persist(entity);
            return entity;
        } else {
            return this.entityManager.merge(entity);
        }
    }

    boolean isChanged(Object before, Object after) throws InvocationTargetException, IllegalAccessException {
        if (before == null && after == null) {
            return false;
        }
        if (before != null && after == null) {
            return true;
        }
        if (before == null && after != null) {
            return true;
        }
        Class c = before.getClass();
        while (c.getSuperclass() != null) {
            for (Method method : c.getDeclaredMethods()) {
                if (Arrays.stream(method.getParameters()).findAny().isPresent()) {
                    continue;
                }
                if (!method.getName().startsWith("get") && !method.getName().startsWith("is")) {
                    continue;
                }
                if (method.getReturnType() == BigDecimal.class) {
                    if (!method.invoke(before).equals(method.invoke(after))) {
                        return true;
                    }
                }
                if (!method.invoke(before).equals(method.invoke(after))) {
                    return true;
                }
                c = c.getSuperclass();
            }
        }

        return false;
    }

    Map<String, PreviousNextDTO> getMapOfChanges(Object before, Object after) throws InvocationTargetException, IllegalAccessException {
        if (before == null && after == null) {
            return Collections.emptyMap();
        }
        if (before != null && after == null) {
            return Collections.emptyMap();
        }
        if (before == null && after != null) {
            return Collections.emptyMap();
        }

        Map<String, PreviousNextDTO> mapOfChanges = new HashMap<>();

        Class c = before.getClass();
        String fieldName = "";
        Object fieldBefore;
        Object fieldAfter;
        while (c.getSuperclass() != null) {
            for (Method method : c.getDeclaredMethods()) {
                if (Arrays.stream(method.getParameters()).findAny().isPresent()) {
                    continue;
                }
                if (!method.getName().startsWith("get") && !method.getName().startsWith("is")) {
                    continue;
                }
                fieldName = method.getName().startsWith("get")
                        ? method.getName().substring(3)
                        : method.getName().startsWith("is")
                        ? method.getName().substring(2)
                        : "";
                fieldBefore = method.invoke(before);
                fieldAfter = method.invoke(after);
                if (fieldBefore == null && fieldAfter == null) {
                    continue;
                }
                if (fieldBefore != null && fieldAfter == null) {
                    return Collections.emptyMap();
                }
                if (fieldBefore == null && fieldAfter != null) {
                    return Collections.emptyMap();
                }
                if (method.getReturnType() == BigDecimal.class) {
                    if (((BigDecimal) fieldBefore).compareTo((BigDecimal) fieldAfter) != 0) {
                        mapOfChanges.put(
                                Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1),
                                PreviousNextDTO.builder()
                                        .previous(fieldBefore.toString())
                                        .next(fieldAfter.toString())
                                        .build()
                        );
                    }
                    continue;
                }
                if (!fieldBefore.equals(fieldAfter)) {
                    mapOfChanges.put(
                            Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1),
                            PreviousNextDTO.builder()
                                    .previous(fieldBefore.toString())
                                    .next(fieldAfter.toString())
                                    .build()
                    );
                }
            }
            c = c.getSuperclass();
        }

        return mapOfChanges;
    }

    @Override
    public Optional<T> findActualArchivalPersonByPerson(Person person) {
        List<T> objs = entityManager.createQuery("""
                        select archivalPerson
                        from ArchivalPerson archivalPerson
                        where archivalPerson.idPerson =:idPerson
                        order by archivalPerson.idArchivalPerson desc
                        limit 1
                        """)
                .setParameter("idPerson", person.getIdPerson())
                .getResultList();
        return objs.isEmpty() ? Optional.empty() : Optional.of(objs.getFirst());
    }

    @Deprecated
    @Override
    public Optional<T> findActualArchivalPersonByPersonName(String personName) {
        List<T> objs = entityManager.createQuery("""
                        select archivalPerson
                        from ArchivalPerson archivalPerson
                        where archivalPerson.name =:personName
                        order by archivalPerson.idArchivalPerson desc
                        limit 1
                        """)
                .setParameter("personName", personName)
                .getResultList();
        return objs.isEmpty() ? Optional.empty() : Optional.of(objs.getFirst());

    }


}