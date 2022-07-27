package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getEmployees(){
        return employeeRepository.getAllEmployee();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id){
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(String gender){
        return employeeRepository.getEmployeesByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addAEmployee(@RequestBody Employee employee){
        return employeeRepository.addAEmployee(employee);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeesByPage(Integer page, Integer pageSize){
        return employeeRepository.getEmployeeByPage(page, pageSize);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee){
        return employeeRepository.updateEmployee(id,employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id){
        employeeRepository.deleteEmployee(id);
    }
}
