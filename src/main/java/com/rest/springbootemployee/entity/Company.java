package com.rest.springbootemployee.entity;

import java.util.List;

public class Company {
    private int id;
    private String name;
    private List<Employee> employees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Company(int id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public void merge(Company company) {
        this.name=company.name;
        this.employees=company.employees;
    }
}
