package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findByID(int id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.getAllEmployee();
    }

    public Employee update(int id, Employee toUpdateEmployee) {
        Employee employee = employeeRepository.findById(id);
        employee.merge(toUpdateEmployee);
        return employeeRepository.updateEmployee(1, employee);
    }

    public Integer addAEmployee(Employee employee) {
        return employeeRepository.addAEmployee(employee);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.getEmployeesByGender(gender);
    }

    public List<Employee> getEmployeeByPage(int page, int pageSize) {
        return employeeRepository.getEmployeeByPage(page,pageSize);
    }
}
