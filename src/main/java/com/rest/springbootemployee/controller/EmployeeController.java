package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id){
        return employeeService.findByID(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(String gender){
        return employeeService.getEmployeesByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addAEmployee(@RequestBody Employee employee){
        return employeeService.addAEmployee(employee);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeesByPage(Integer page, Integer pageSize){
        return employeeService.getEmployeeByPage(page, pageSize);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee){
        return employeeService.update(id,employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id){
        employeeService.deleteEmployee(id);
    }
}
