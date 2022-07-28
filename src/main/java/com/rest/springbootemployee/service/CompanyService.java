package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.CompanyNotFoundException;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {


    @Autowired
    JpaCompanyRepository jpaCompanyRepository;

    public List<Company> getAllCompanies() {
        return jpaCompanyRepository.findAll();
    }

    public Company getCompanyByID(int id) {
        return jpaCompanyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> getCompanyByPage(int page, int pageSize) {
        return jpaCompanyRepository.findAll(PageRequest.of(page,pageSize)).toList();
    }

    public Company addCompany(Company company) {
        return jpaCompanyRepository.save(company);
    }

    public Company update(int id, Company toUpdateCompany) {
        Company company=jpaCompanyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        company.merge(toUpdateCompany);
        return jpaCompanyRepository.save(company);
    }

    public void deleteCompany(int id) {
        jpaCompanyRepository.deleteById(id);
    }

    public List<Employee> getCompanyEmployeesByID(int companyID) {
        return jpaCompanyRepository.getEmployeesByCompanyId(companyID);
    }
}
