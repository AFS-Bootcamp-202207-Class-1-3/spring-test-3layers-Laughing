package com.rest.springbootemployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException() {
        super("employee not found");
    }
}