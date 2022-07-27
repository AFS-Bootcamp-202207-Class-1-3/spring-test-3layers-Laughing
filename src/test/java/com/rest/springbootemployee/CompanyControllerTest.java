package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void cleanDB() {
        companyRepository.cleanAll();
    }

    @Test
    public void should_return_all_companies_when_find_all_given_companies() throws Exception {
        List<Employee> employeeList = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Kendrick", 22, "male", 20000));
                add(new Employee(2, "Kenssdrick", 12, "male", 30000));
                add(new Employee(3, "Kenddxrick", 22, "female", 20000));
            }
        };
        Company company = new Company(1, "cool", employeeList);
        companyRepository.addCompany(company);

        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("cool"));
    }

    @Test
    public void should_return_company_when_getCompanyByID_given_id() throws Exception {
        companyRepository.addCompany(new Company(1, "cool",new ArrayList<Employee>()));

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("cool"));
    }
}
