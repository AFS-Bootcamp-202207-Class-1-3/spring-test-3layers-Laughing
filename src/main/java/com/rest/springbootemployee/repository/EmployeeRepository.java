package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.exception.EmployeeNotFoundException;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    List<Employee> employeeRepository;

    public EmployeeRepository() {
        employeeRepository = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Kendrick", 22, "male", 1, 20000));
                add(new Employee(2, "Kendrick", 22, "male", 1, 20000));
                add(new Employee(3, "Kendrick", 22, "female", 1, 20000));
                add(new Employee(4, "Kendrick", 22, "male", 1, 50000));
                add(new Employee(5, "Kendrick", 22, "female", 1, 30000));
                add(new Employee(6, "Kendrick", 22, "male", 1, 20000));
            }
        };
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository;
    }

    public Employee findById(int id) {
        return employeeRepository.stream().
                filter(employee -> employee.getId() == id).
                findFirst().
                orElseThrow(() -> new EmployeeNotFoundException());
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.stream().
                filter(employee -> employee.getGender().equals(gender)).
                collect(Collectors.toList());
    }

    public Integer addAEmployee(Employee employee) {
        int newId = generateMaxId();
        employee.setId(newId);
        employeeRepository.add(employee);
        return newId;
    }

    private int generateMaxId() {
        return employeeRepository.stream().
                mapToInt(employee -> employee.getId()).max().
                orElse(0) + 1;
    }


    public List<Employee> getEmployeeByPage(int page, int pageSize) {
        return employeeRepository.stream()
                .skip((page - 1) * pageSize).
                limit(pageSize).collect(Collectors.toList());
    }

    public Employee updateEmployee(int id, Employee employee) {
        return employee;
    }

    public void deleteEmployee(int id) {
        Employee employee = findById(id);
        employeeRepository.remove(employee);
    }

    public void cleanAll() {
        employeeRepository.clear();
    }
}
