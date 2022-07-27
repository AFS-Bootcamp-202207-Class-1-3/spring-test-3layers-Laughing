package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

//    @Mock
    @Spy
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_return_all_employees_when_find_all_given_employees() {

        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(employeeRepository.getAllEmployee()).willReturn(employeeList);

        List<Employee> employees = employeeService.getAllEmployee();

        assertThat(employees, hasSize(1));
    }

    @Test
    public void should_return_updated_employee_when_update_given_employee() {
        int newSalary = 1999;
        Employee originEmployee = new Employee(1, "laughing", 22, "male", 80000);
        Employee toUpdateEmployee = new Employee(1, "laughing", 22, "male", newSalary);

        given(employeeRepository.findById(1)).willReturn(originEmployee);

        given(employeeRepository.updateEmployee(1, toUpdateEmployee)).willCallRealMethod();

        Employee updateEmployee = employeeService.update(1, toUpdateEmployee);

        verify(employeeRepository).updateEmployee(1,originEmployee);

    }

    @Test
    public void should_return_employee_when_get_employee_by_id_given_id(){
        int id=1;
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(employeeRepository.findById(id)).willReturn(employee);

        Employee employee2=employeeService.findByID(id);

        assertThat(employee,equalTo(employee2));

    }
    @Test
    public void should_return_employee_when_create_employee_given_new_employee(){
        Employee employee = new Employee(0, "Kendrick", 22, "male", 20000);
        int resultId=1;
        given(employeeRepository.addAEmployee(employee)).willReturn(resultId);

        int newId=employeeService.addAEmployee(employee);
        assertThat(newId,equalTo(resultId));
    }
}
