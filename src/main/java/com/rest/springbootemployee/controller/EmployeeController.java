package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.mapper.EmployeeMapper;
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
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.findByID(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addAEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return EmployeeMapper.entityToResponse(employeeService.addAEmployee(EmployeeMapper.requestToEntity(employeeRequest)));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
        return employeeService.getEmployeeByPage(page, pageSize);
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        return EmployeeMapper.entityToResponse(employeeService.update(id, EmployeeMapper.requestToEntity(employeeRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }
}
