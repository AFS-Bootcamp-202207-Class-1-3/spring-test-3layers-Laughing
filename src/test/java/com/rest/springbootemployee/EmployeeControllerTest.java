package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFoundException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void cleanDB() {
        employeeRepository.cleanAll();
    }

    @Test
    public void should_return_employees_when_getAllEmployees() throws Exception {
        employeeRepository.addAEmployee(new Employee(1, "Kendrick", 22, "male", 20000));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Kendrick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(20000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22));
    }

    @Test
    public void should_create_new_employee_when_perform_post_given_new_employee() throws Exception {
        String newEmployee="{\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Kendraxxxxick\",\n" +
                "                \"age\": 12,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 30000\n" +
                "            }";

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON).content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendraxxxxick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(30000));
    }

    @Test
    public void should_return_employee_when_getEmployeeByID_given_id() throws Exception {
        employeeRepository.addAEmployee(new Employee(1, "Kendrick", 22, "male", 20000));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
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
                     .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                     .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
                    .andExpect(result -> assertEquals("employee not found", result.getResolvedException().getMessage()));

    }

    @Test
    public void should_return_employee_when_put_employee_given_id_employee() throws Exception {
        employeeRepository.addAEmployee(new Employee(1, "Kendraxxxxick", 22, "male", 20000));

        int id=1;
        String employee="{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Kendraxxxxick\",\n" +
                "                \"age\": 12,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 9999\n" +
                "            }";

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/"+id)
                        .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kendraxxxxick"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(9999));

    }

    @Test
    public void should_return_employee_not_found_exception_when_put_not_found_id_employee() throws Exception {
        employeeRepository.addAEmployee(new Employee(1, "Kendraxxxxick", 22, "male", 20000));

        int id=2;
        String employee="{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Kendraxxxxick\",\n" +
                "                \"age\": 12,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 9999\n" +
                "            }";

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
                .andExpect(result -> assertEquals("employee not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void should_return_nothing_when_delete_employee_given_id() throws Exception {

        int id=1;
        employeeRepository.addAEmployee(new Employee(1, "Kendraxxxxick", 22, "male", 20000));

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}
