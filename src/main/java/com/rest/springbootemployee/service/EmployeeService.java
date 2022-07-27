package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    public List<Employee> getAllEmployee() {
        return employeeRepository.getAllEmployee();
    }

    public Employee update(int id, Employee toUpdateEmployee) {
        Employee employee = employeeRepository.findById(id);
        employee.setSalary(toUpdateEmployee.getSalary());
        return employeeRepository.updateEmployee(1, employee);
    }
}
