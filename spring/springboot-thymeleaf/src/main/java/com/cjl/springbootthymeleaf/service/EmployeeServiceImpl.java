package com.cjl.springbootthymeleaf.service;

import com.cjl.springbootthymeleaf.dao.EmployeeRepository;
import com.cjl.springbootthymeleaf.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // private EmployeeDAO employeeDAO;

    // Spring data jpa - given basic CRUD for free
    private EmployeeRepository employeeRepository;

    // @Autowired
    // public EmployeeServiceImpl(@Qualifier("employeeDAOJpaImpl") EmployeeDAO employeeDAO) {
    //     this.employeeDAO = employeeDAO;
    // }

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    @Transactional
    public Employee findById(int id) {
        Optional<Employee> res = employeeRepository.findById(id);

        Employee employee = null;

        if (res.isPresent()) {
            employee = res.get();
        } else {
            throw new RuntimeException("Could not find employee with id: " + id);
        }

        return employee;
    }

    @Override
    @Transactional
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }
}
