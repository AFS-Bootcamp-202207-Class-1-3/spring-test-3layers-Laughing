package com.rest.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.controller.EmployeeRequest;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFoundException;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaEmployeeRepository jpaEmployeeRepository;

    @Autowired
    private JpaCompanyRepository jpaCompanyRepository;

    private int companyId;

    @BeforeEach
    public void cleanDB() {
        jpaEmployeeRepository.deleteAll();
        jpaEmployeeRepository.flush();
        Company company = new Company();
        company.setName("cool");
        companyId = jpaCompanyRepository.save(company).getId();
    }

    @Test
    public void should_return_employees_when_getAllEmployees() throws Exception {
        jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", companyId, 20000));
        jpaEmployeeRepository.save(new Employee(2, "Kendrick", 22, "female", companyId, 20000));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(20000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22));
    }

    @Test
    public void should_return_new_employee_when_perform_post_given_new_employee() throws Exception {

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setAge(22);
        employeeRequest.setName("lily");
        employeeRequest.setGender("female");
        employeeRequest.setSalary(10000);
        employeeRequest.setCompanyId(companyId);

        ObjectMapper objectMapper = new ObjectMapper();
        String newEmployee = objectMapper.writeValueAsString(employeeRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON).content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("lily"));

    }

    @Test
    public void should_return_employee_when_getEmployeeByID_given_id() throws Exception {
        Employee employee = jpaEmployeeRepository.saveAndFlush(new Employee(1, "Kendrick", 22, "male", companyId, 20000));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(20000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22));
    }

    @Test
    public void should_return_employeeNotFoundException_when_getEmployeeByID_given_not_found_id() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
                .andExpect(result -> assertEquals("employee not found", result.getResolvedException().getMessage()));

    }

    @Test
    public void should_return_employee_when_put_employee_given_id_employee() throws Exception {
        int id = jpaEmployeeRepository.save(new Employee(1, "Kendraxxxxick", 22, "male", companyId, 20000)).getId();

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setAge(22);
        employeeRequest.setName("lily");
        employeeRequest.setGender("female");
        employeeRequest.setSalary(10000);
        employeeRequest.setCompanyId(companyId);

        ObjectMapper objectMapper = new ObjectMapper();
        String newEmployee = objectMapper.writeValueAsString(employeeRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON).content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22));

    }

    @Test
    public void should_return_employee_not_found_exception_when_put_not_found_id_employee() throws Exception {

        int id = 1;
        String employee = "{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Kendraxxxxick\",\n" +
                "                \"age\": 12,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 9999\n" +
                "            }";

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
                .andExpect(result -> assertEquals("employee not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void should_return_is_no_content_when_delete_employee_given_id() throws Exception {

        int id;
        id = jpaEmployeeRepository.saveAndFlush(new Employee(1, "Kendraxxxxick", 22, "male", companyId, 20000)).getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void should_return_employees_by_page_when_getEmployees_by_page() throws Exception {
        jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", companyId, 20000));
        jpaEmployeeRepository.save(new Employee(2, "Kendrick", 12, "female", companyId, 20500));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees?page=1&pageSize=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void should_return_employee_when_getEmployeeByGender_given_gender() throws Exception {
        jpaEmployeeRepository.save(new Employee(1, "Kendrick", 22, "male", companyId, 20000));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender=male"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"));
    }
}