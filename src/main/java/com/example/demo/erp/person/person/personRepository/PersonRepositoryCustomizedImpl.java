package com.example.demo.erp.person.person.personRepository;

import com.example.demo.erp.*;
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

public class PersonRepositoryCustomizedImpl<T, ID> implements PersonRepositoryCustomized<T, ID> {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Override
    @Transactional
    public <S extends T> S saveAndCopy(S entity) {
        return this.save(entity);
    }

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        Assert.notNull(entity, "Entity must not be null");
        if (((Person) entity).getEmployeeDetails() != null) {
            if (((Person) entity).getEmployeeDetails().getIdEmployeeDetails() == null) {
                EmployeeDetails employeeDetails = new EmployeeDetails(((Person) entity).getEmployeeDetails());
                this.entityManager.persist(employeeDetails);
                ((Person)entity).setEmployeeDetails(employeeDetails);
            } else {
                EmployeeDetails employeeDetails =
                        employeeDetailsRepository.findById(((Person) entity).getEmployeeDetails().getIdEmployeeDetails())
                                .orElseThrow();

                boolean isChanged = false;
                Map<String, PreviousNextDTO> map = Collections.emptyMap();
                try {
                    isChanged = this.isChanged(employeeDetails, ((Person) entity).getEmployeeDetails());
                    map = getMapOfChanges(employeeDetails, ((Person) entity).getEmployeeDetails());
                } catch (Exception ignored) {
                }

                if (isChanged) {
                    ((Person) entity).getEmployeeDetails().setIdEmployeeDetails(null);
                    this.entityManager.persist(((Person) entity).getEmployeeDetails());
                }
            }
        }


//        Class c = entity.getClass();
//        while (c.getSuperclass() != null) {
//            for (Method method : c.getDeclaredMethods()) {
//                String methodName = method.getName();
//                if (method.getName().length() < 4) {
//                    continue;
//                }
//                if (!method.getName().startsWith("get")) {
//                    continue;
//                }
//                if (Arrays.stream(method.getParameters()).findAny().isPresent()) {
//                    continue;
//                }
//                try {
//                    Object o = method.invoke(entity);
//                    if (Object.class.isAssignableFrom(o.getClass())) {
//                        for (Method method1 : o.getClass().getDeclaredMethods()) {
//                            if (method1.getName().length() < 5) {
//                                continue;
//                            }
//                            if (method1.getName().length() == 5) {
//                                if (!method1.getName().equals("setId")) {
//                                    continue;
//                                }
//                            } else {
//                                if (Character.isLowerCase(method1.getName().charAt(5))) {
//                                    continue;
//                                }
//                            }
//                            if (Arrays.stream(method1.getParameters()).count() != 1) {
//                                continue;
//                            }
//                            if (
//                                    !method1.getName().startsWith(
//                                            "setId" +
//                                                    Character.toUpperCase(o.getClass().getSimpleName().charAt(0)) +
//                                                    o.getClass().getSimpleName().substring(1)
//                                    )
//                            ) {
//                                continue;
//                            } else {
//                                int i = 5;
//                                i++;
//                            }
//                            if (method.getParameters().length != 1) {
//                                continue;
//                            }
//                            method1.invoke(o, null);
//
//                        }
//                    }
//                } catch (Exception ignored) {
//                }
//
//            }
//            c = c.getSuperclass();
//        }

        if (((Person) entity).getIdPerson() == null) {
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

}