package com.cjl.springbootthymeleaf.dao;

import com.cjl.springbootthymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Just specify entity and primary key
// Spring gives jpa repository with basic CRUD
// @RepositoryRestResource(path="members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Spring data jpa will parse method name and look for specific format/pattern
    // Creates appropriate query for db
    public List<Employee> findAllByOrderByLastNameAsc();
}
