package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EmployeeServiceTest {

    //    @Mock
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private JpaEmployeeRepository jpaEmployeeRepository;

    @Test
    public void should_return_all_employees_when_find_all_given_employees() {

        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(jpaEmployeeRepository.findAll()).willReturn(employeeList);

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

        verify(employeeRepository).updateEmployee(1, originEmployee);

    }

    @Test
    public void should_return_employee_when_get_employee_by_id_given_id() {
        int id = 1;
        Employee employee = new Employee(id, "Kendrick", 22, "male", 20000);

        given(jpaEmployeeRepository.findById(id)).willReturn(Optional.of(employee));

        Employee employee2 = employeeService.findByID(id);

        assertThat(employee, equalTo(employee2));

    }

    @Test
    public void should_return_employee_when_create_employee_given_new_employee() {
        Employee employee = new Employee(1, "Kendrick", 22, "male", 20000);
        given(jpaEmployeeRepository.save(employee)).willReturn(employee);

        Employee employee2 = employeeService.addAEmployee(employee);
        assertThat(employee, equalTo(employee2));
    }

    @Test
    public void should_return_employee_when_getEmployeeByGender_given_gender() {
        Employee employee = new Employee(0, "Kendrick", 22, "male", 20000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        String gender = "male";
        given(jpaEmployeeRepository.getByGender(gender)).willReturn(employees);

        List<Employee> employeeList = employeeService.getEmployeesByGender(gender);

        assertThat(employeeList, hasSize(1));
        assertThat(employeeList.get(0).getGender(), equalTo(gender));
    }

    @Test
    public void should_return_employees_by_page_when_getEmployees_by_page() {
        Employee employee1 = new Employee(1, "Kendrick", 22, "male", 20000);
        Employee employee2 = new Employee(2, "Kendrick", 22, "male", 20000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        int page = 1, pageSize = 2;
        Pageable pageObj= PageRequest.of(page, pageSize);
        given(jpaEmployeeRepository.findAll(pageObj)).willReturn(new PageImpl<>(employeeList));

        List<Employee> employees = employeeService.getEmployeeByPage(page,pageSize);

        assertThat(employees,hasSize(2) );
    }

    @Test
    public void should_return_is_no_content_when_delete_employee_given_id(){
        int id=1;
        employeeService.deleteEmployee(id);
        verify(employeeRepository).deleteEmployee(id);
    }
}
