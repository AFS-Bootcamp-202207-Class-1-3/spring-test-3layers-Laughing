package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.controller.CompanyRequest;
import com.rest.springbootemployee.controller.CompanyResponse;
import com.rest.springbootemployee.controller.EmployeeRequest;
import com.rest.springbootemployee.controller.EmployeeResponse;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {
    public static Company requestToEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public static CompanyResponse entityToResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        return companyResponse;
    }

    public static List<CompanyResponse> entityToResponseList(List<Company> companies) {
        return companies.stream().map(company -> {
            CompanyResponse companyResponse = new CompanyResponse();
            BeanUtils.copyProperties(company, companyResponse);
            return companyResponse;
        }).collect(Collectors.toList());
    }
}
