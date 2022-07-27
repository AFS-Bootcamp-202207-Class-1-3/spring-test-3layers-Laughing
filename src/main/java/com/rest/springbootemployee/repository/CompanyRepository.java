package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private List<Company> companyRepository;

    public CompanyRepository(){

        List<Employee> employeeList1 = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Kendrick", 22, "male", 20000));
                add(new Employee(2, "Kenssdrick", 12, "male", 30000));
                add(new Employee(3, "Kenddxrick", 22, "female", 20000));
            }
        };
        List<Employee> employeeList2 = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Laughing", 22, "male", 20000));
                add(new Employee(2, "Kendraxxxxick", 12, "male", 30000));
                add(new Employee(3, "Laughinggggg", 22, "female", 20000));
            }
        };

        companyRepository = new ArrayList<Company>(){
            {
                add(new Company(1,"cool",employeeList1));
                add(new Company(2,"hot",employeeList2));
            }
        };
    }

    public List<Company> getAllCompanies() {
        return companyRepository;
    }

    public Company getCompanyByID(int id) {
        return companyRepository.stream().
                filter(company -> company.getId()==id).
                findFirst().get();
    }

    public List<Employee> getCompanyEmployeesByID(int id) {
        return getCompanyByID(id).getEmployees();
    }

    public List<Company> getCompaniesByPage(int page, int pageSize) {
        return companyRepository.stream()
                .skip((page - 1) * pageSize).
                limit(pageSize).collect(Collectors.toList());
    }

    public Company addCompany(Company company) {
        company.setId(generateId());
        companyRepository.add(company);
        return company;
    }

    private int generateId() {
        return companyRepository.stream()
                .mapToInt(company -> company.getId())
                .max().orElse(0) + 1;
    }

    public Company updateCompany(int id, Company company) {
        Company updateCompany=getCompanyByID(id);
        updateCompany.merge(company);
        return updateCompany;
    }

    public void deleteCompany(int id) {
       Company company=getCompanyByID(id);
       companyRepository.remove(company);
    }
}
