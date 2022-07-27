package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_return_all_employees_when_find_all_given_employees(){

        Employee employee=new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList=new ArrayList<>();
        employeeList.add(employee);

        given(employeeRepository.getAllEmployee()).willReturn(employeeList);

        List<Employee> employees=employeeService.getAllEmployee();

        assertThat(employees,hasSize(1));
    }
}
