package com.cjl.springbootcrud.dao;

import com.cjl.springbootcrud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Just specify entity and primary key
// Spring gives jpa repository with basic CRUD
// @RepositoryRestResource(path="members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
