package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JpaCompanyRepository extends JpaRepository<Company, Integer> {

    @Query(value = "SELECT e FROM Employee e WHERE e.companyId=?1")
    List<Employee> getEmployeesByCompanyId(Integer companyID);
}