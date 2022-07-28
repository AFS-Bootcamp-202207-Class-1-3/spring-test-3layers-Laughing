package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFoundException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JpaEmployeeRepository jpaEmployeeRepository;

    public Employee findByID(int id) {
        return jpaEmployeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getAllEmployee() {
        return jpaEmployeeRepository.findAll();
    }

    public Employee update(int id, Employee toUpdateEmployee) {
        Employee employee = employeeRepository.findById(id);
        employee.merge(toUpdateEmployee);
        return employeeRepository.updateEmployee(1, employee);
    }

    public Employee addAEmployee(Employee employee) {
        return jpaEmployeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return jpaEmployeeRepository.getByGender(gender);
    }

    public List<Employee> getEmployeeByPage(int page, int pageSize) {
        return jpaEmployeeRepository.findAll(PageRequest.of(page,pageSize)).toList();
    }

    public void deleteEmployee(int id) {
        jpaEmployeeRepository.deleteById(id);
    }
}
