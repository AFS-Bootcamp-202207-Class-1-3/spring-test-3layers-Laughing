package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

public class CompanyRequest {
    private String name;

    public CompanyRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
