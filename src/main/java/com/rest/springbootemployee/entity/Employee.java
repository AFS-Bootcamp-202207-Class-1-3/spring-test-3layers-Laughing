package com.rest.springbootemployee.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    private String gender;

    private int companyId;

    public Employee() {
    }

    public Employee(int id, String name, int age, String gender, int companyId, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.companyId = companyId;
        this.salary = salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getSalary() {
        return salary;
    }

    private int salary;

    public void merge(Employee employee) {
        this.name=employee.getName();
        this.age=employee.getAge();
        this.gender=employee.getGender();
        this.salary=employee.getSalary();
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}