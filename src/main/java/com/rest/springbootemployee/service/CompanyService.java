package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.getAllCompanies();
    }

    public Company getCompanyByID(int id) {
        return companyRepository.getCompanyByID(id);
    }

    public List<Company> getCompanyByPage(int page, int pageSize) {
        return companyRepository.getCompaniesByPage(page,pageSize);
    }
}
