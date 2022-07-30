package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.controller.EmployeeRequest;
import com.rest.springbootemployee.controller.EmployeeResponse;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static Employee requestToEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public static EmployeeResponse entityToResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }

    public static List<EmployeeResponse> entityToResponseList(List<Employee> employees) {
        return employees.stream().map(employee -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            BeanUtils.copyProperties(employee, employeeResponse);
            return employeeResponse;
        }).collect(Collectors.toList());
    }
}
