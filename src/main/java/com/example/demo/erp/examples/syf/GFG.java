package com.example.demo.erp.examples.syf;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.logging.Logger;


public class GFG {
    private static final Logger LOGGER = Logger.getLogger(GFG.class.getName());

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class c = Class.forName(Emp.class.getName());

        Method[] methods = c.getDeclaredMethods();

        Emp emp = new Emp();

        for (Field field : c.getFields()) {
            System.out.println("field : " + field);

            if (field.getType() == BigDecimal.class) {
                Method method1 = Emp.class.getDeclaredMethod(getSetterName(field.getName(), methods),BigDecimal.class);
                method1.invoke(emp,new BigDecimal("123.45"));
                getSetterName(field.getName(), methods);
            }

        }




        //        Constructor array
        Constructor[] constructors = c.getDeclaredConstructors();



        for (Constructor constructor : constructors) {
            System.out.println("Name of Constructor : " + constructor);

            System.out.println("Count of constructor parameter : " + constructor.getParameterCount());

            Parameter[] parameters = constructor.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println("Constructor's parameter : " + parameter);
            }
            System.out.println();
        }
        System.out.println();
//          Method Array
        System.out.println("Length of method : " + methods.length);

        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println("Method's Parameter : " + parameter);
                if (parameter.getType() == BigDecimal.class) {
                    Method method1 = Emp.class.getDeclaredMethod(getSetterName(parameter.getName(), methods),BigDecimal.class);
                    method1.invoke(emp,new BigDecimal("123.45"));
                    getSetterName(parameter.getName(), methods);
                }
            }
            System.out.println();
        }
        System.out.println();
//        Annotations
        Class[] classes = c.getDeclaredClasses();
        for (Class class1 : classes) {
            System.out.println("class: " + class1);
            System.out.println("Name of class: " + class1.getName());
        }
//        Annotations
        Annotation[] anno = c.getDeclaredAnnotations();
        for (Annotation annotation : anno) {
            System.out.println("Annotation: " + annotation);
        }

    }


    private static String getSetterName(String fieldName, Method[] methods) {
        for (Method method : methods) {
            if (method.getName().equals(
                    "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1)
            )) {
                return method.getName();
            }
        }
        return null;
    }


}