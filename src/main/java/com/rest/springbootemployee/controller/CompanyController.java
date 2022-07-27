package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public List<Company> getCompanies(){
        return companyRepository.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id){
        return companyRepository.getCompanyByID(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployeesByCompanyId(@PathVariable Integer id){
        return companyRepository.getCompanyEmployeesByID(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> getCompaniesByPage(Integer page, Integer pageSize){
        return companyRepository.getCompaniesByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addCompany(@RequestBody Company company){
        return companyRepository.addCompany(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company){
        return companyRepository.updateCompany(id,company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Integer id){
        companyRepository.deleteCompany(id);
    }
}
