package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Spy
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void should_return_all_companies_when_find_all_given_companies() {

        List<Employee> employeeList = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Kendrick", 22, "male", 20000));
                add(new Employee(2, "Kenssdrick", 12, "male", 30000));
                add(new Employee(3, "Kenddxrick", 22, "female", 20000));
            }
        };
        Company company = new Company(1, "cool", employeeList);
        List<Company> companyList = new ArrayList<>();
        companyList.add(company);

        given(companyRepository.getAllCompanies()).willReturn(companyList);

        List<Company> companies = companyService.getAllCompanies();

        assertThat(companies, hasSize(1));
    }


    @Test
    public void should_return_company_when_get_company_by_id_given_id() {
        int id = 1;
        List<Employee> employeeList = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Kendrick", 22, "male", 20000));
                add(new Employee(2, "Kenssdrick", 12, "male", 30000));
                add(new Employee(3, "Kenddxrick", 22, "female", 20000));
            }
        };
        Company company = new Company(id, "cool", employeeList);

        given(companyRepository.getCompanyByID(id)).willReturn(company);

       Company findCompany=companyService.getCompanyByID(id);

        assertThat(company, equalTo(findCompany));

    }

    @Test
    public void should_return_companies_by_page_when_getCompanies_by_page() {
        Company company = new Company(1, "cool", new ArrayList<>());
        List<Company> companyList = new ArrayList<>();
        companyList.add(company);
        int page = 1, pageSize = 1;
        given(companyRepository.getCompaniesByPage(page,pageSize)).willReturn(companyList);

        List<Company> companies = companyService.getCompanyByPage(page,pageSize);

        assertThat(companies.get(0),equalTo(company) );
    }
}
