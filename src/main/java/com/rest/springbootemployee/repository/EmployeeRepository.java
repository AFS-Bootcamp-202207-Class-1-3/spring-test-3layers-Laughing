package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.EmployeeNotFoundException;
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
                add(new Employee(1, "Kendrick", 22, "male", 20000));
                add(new Employee(2, "Kendrick", 22, "male", 20000));
                add(new Employee(3, "Kendrick", 22, "female", 20000));
                add(new Employee(4, "Kendrick", 22, "male", 50000));
                add(new Employee(5, "Kendrick", 22, "female", 30000));
                add(new Employee(6, "Kendrick", 22, "male", 20000));
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

    public Employee addAEmployee(Employee employee) {
        employee.setId(generateMaxId());
        employeeRepository.add(employee);
        return employee;
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
        Employee updateEmployee = this.findById(id);
        updateEmployee.merge(employee);
        return updateEmployee;
    }

    public void deleteEmployee(int id) {
        Employee employee=findById(id);
        employeeRepository.remove(employee);
    }

    public void cleanAll() {
        employeeRepository.clear();
    }
}
