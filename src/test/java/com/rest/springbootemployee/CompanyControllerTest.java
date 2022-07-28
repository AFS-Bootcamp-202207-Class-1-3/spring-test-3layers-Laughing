package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.CompanyNotFoundException;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JpaCompanyRepository jpaCompanyRepository;

    @BeforeEach
    public void cleanDB() {
        jpaCompanyRepository.deleteAll();
    }

    @Test
    public void should_return_all_companies_when_find_all_given_companies() throws Exception {
        Company company = new Company(1, "cool", new ArrayList<>());
        jpaCompanyRepository.save(company);

        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("cool"));
    }

    @Test
    public void should_return_company_when_getCompanyByID_given_id() throws Exception {
        Company company=jpaCompanyRepository.save(new Company(1, "cool",new ArrayList<Employee>()));

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}",company.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("cool"));
    }

    @Test
    public void should_return_companyNotFoundException_when_getCompanyByID_given_not_found_id() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyNotFoundException))
                .andExpect(result -> assertEquals("company not found", result.getResolvedException().getMessage()));

    }

    @Test
    public void should_return_companies_by_page_when_getCompanies_by_page() throws Exception {
        companyRepository.addCompany(new Company(1, "cool", new ArrayList<>()));
        companyRepository.addCompany(new Company(1, "cool", new ArrayList<>()));

        mockMvc.perform(MockMvcRequestBuilders.get("/companies?page=1&pageSize=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void should_return_new_companyId_when_perform_post_given_new_company() throws Exception {
        String newCompany = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"cool\",\n" +
                "        \"employees\": [\n" +
                "        ]\n" +
                "    }";

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON).content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertEquals(response,"1");
    }

    @Test
    public void should_return_company_when_put_company_given_id_company() throws Exception {
        companyRepository.addCompany(new Company(1, "cool", new ArrayList<>()));

        int id = 1;
        String newCompany = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"cool\",\n" +
                "        \"employees\": [\n" +
                "        ]\n" +
                "    }";

        mockMvc.perform(MockMvcRequestBuilders.put("/companies/" + id)
                        .contentType(MediaType.APPLICATION_JSON).content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("cool"));

    }

    @Test
    public void should_return_is_no_content_when_delete_company_given_id() throws Exception {

        int id = 1;
        companyRepository.addCompany(new Company(1, "cool", new ArrayList<>()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void should_return_employees_when_get_company_employees_given_company_id() throws Exception {
        List<Employee> employeeList = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Kendrick", 22, "male",1, 20000));
                add(new Employee(2, "Kenssdrick", 12, "male",1, 30000));
                add(new Employee(3, "Kenddxrick", 22, "female",1, 20000));
            }
        };
        Company company = new Company(1, "cool", employeeList);
        companyRepository.addCompany(company);
        int id=1;
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}/employees",id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(3)));
    }
}
