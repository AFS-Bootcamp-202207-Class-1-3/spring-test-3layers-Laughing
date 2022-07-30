package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.mapper.CompanyMapper;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<CompanyResponse> getCompanies() {
        return CompanyMapper.entityToResponseList(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable Integer id) {
        return CompanyMapper.entityToResponse(companyService.getCompanyByID(id));
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployeesByCompanyId(@PathVariable Integer id) {
        return companyService.getCompanyEmployeesByID(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompaniesByPage(Integer page, Integer pageSize) {
        return CompanyMapper.entityToResponseList(companyService.getCompanyByPage(page, pageSize));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest company) {
        return CompanyMapper.entityToResponse(companyService.addCompany(CompanyMapper.requestToEntity(company)));
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompany(@PathVariable Integer id, @RequestBody CompanyRequest company) {
        return CompanyMapper.entityToResponse(companyService.update(id, CompanyMapper.requestToEntity(company)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Integer id) {
        companyService.deleteCompany(id);
    }
}
