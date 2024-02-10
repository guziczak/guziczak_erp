package com.example.demo.erp.examples.syf;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;


public class Emp{
    private int eid;
    private double esal;
    private String ename;
    public BigDecimal bigDecimal;

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    enum Week{
        SUN,TUE,WED;
    }

    @interface MyAnno{}

    public int getEid(){
        return eid;
    }
    public void setEid(int eid,int num, char ch){
        this.eid = eid;
    }

    public double getEsal(){
        return esal;
    }
    public void setEsal(double esal,float data, String name){
        this.esal = esal;
    }

    public String getEname(){
        return ename;
    }
    public void setEname(String ename){
        this.ename = ename;
    }

    // constructor
    public Emp(int eid, double esal, String ename, BigDecimal bigDecimal){
        super();
        this.eid = eid;
        this.esal = esal;
        this.ename = ename;
        this.bigDecimal = bigDecimal;
    }
    Emp(){
    }
    class A{
    }
    class B{
    }
}