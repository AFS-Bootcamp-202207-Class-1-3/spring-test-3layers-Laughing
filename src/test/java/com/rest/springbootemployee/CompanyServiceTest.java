package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
public class CompanyServiceTest {
    @Spy
    private CompanyRepository companyRepository;

    @Mock
    private JpaCompanyRepository jpaCompanyRepository;
    @InjectMocks
    private CompanyService companyService;

    @Test
    public void should_return_all_companies_when_find_all_given_companies() {

        List<Employee> employeeList = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Kendrick", 22, "male", 1, 20000));
                add(new Employee(2, "Kenssdrick", 12, "male", 1, 30000));
                add(new Employee(3, "Kenddxrick", 22, "female", 1, 20000));
            }
        };
        Company company = new Company(1, "cool", employeeList);
        List<Company> companyList = new ArrayList<>();
        companyList.add(company);

        given(jpaCompanyRepository.findAll()).willReturn(companyList);

        List<Company> companies = companyService.getAllCompanies();

        assertThat(companies, hasSize(1));
    }


    @Test
    public void should_return_company_when_get_company_by_id_given_id() {
        int id = 1;

        Company company = new Company(id, "cool", new ArrayList<>());

        given(jpaCompanyRepository.findById(id)).willReturn(Optional.of(company));

        Company findCompany = companyService.getCompanyByID(id);

        assertThat(company, equalTo(findCompany));

    }

    @Test
    public void should_return_companies_by_page_when_getCompanies_by_page() {
        Company company = new Company(1, "cool", new ArrayList<>());
        List<Company> companyList = new ArrayList<>();
        companyList.add(company);
        int page = 1, pageSize = 1;
        given(jpaCompanyRepository.findAll(PageRequest.of(page, pageSize))).willReturn(new PageImpl<>(companyList));

        List<Company> companies = companyService.getCompanyByPage(page, pageSize);

        assertThat(companies.get(0), equalTo(company));
    }

    @Test
    public void should_return_company_when_create_company_given_new_company() {
        Company company = new Company(1, "cool", new ArrayList<>());
        int resultId = 1;
        given(jpaCompanyRepository.save(company)).willReturn(company);

        int newId = companyService.addCompany(company).getId();
        assertThat(newId, equalTo(resultId));
    }

    @Test
    public void should_return_updated_company_when_update_given_company() {
        String newName = "hot";
        Company originCompany = new Company(1, "cool", new ArrayList<>());
        Company toUpdateCompany = new Company(1, newName, new ArrayList<>());


        given(jpaCompanyRepository.findById(1)).willReturn(Optional.of(originCompany));

        companyService.update(1, toUpdateCompany);

        verify(jpaCompanyRepository).save(originCompany);

    }

    @Test
    public void should_return_is_no_content_when_delete_company_given_id() {
        int id = 1;
        companyService.deleteCompany(id);
        verify(companyRepository).deleteCompany(id);
    }

    @Test
    public void should_return_employees_when_get_company_employees_given_company_id() {
        int companyID = 1;
        List<Employee> employeeList = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Kendrick", 22, "male", 1, 20000));
                add(new Employee(2, "Kenssdrick", 12, "male", 1, 30000));
                add(new Employee(3, "Kenddxrick", 22, "female", 1, 20000));
            }
        };

        given(companyRepository.getCompanyEmployeesByID(companyID)).willReturn(employeeList);
        List<Employee> employees = companyService.getCompanyEmployeesByID(companyID);

        assertThat(employees, hasSize(3));
    }
}
