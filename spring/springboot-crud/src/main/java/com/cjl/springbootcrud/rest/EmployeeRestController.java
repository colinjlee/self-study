// File not needed - using spring data rest
// package com.cjl.springbootcrud.rest;
//
// import com.cjl.springbootcrud.entity.Employee;
// import com.cjl.springbootcrud.service.EmployeeService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.List;
//
// @RestController
// @RequestMapping("/api")
// public class EmployeeRestController {
//
//     private EmployeeService employeeService;
//
//     @Autowired
//     public EmployeeRestController(EmployeeService employeeService) {
//         this.employeeService = employeeService;
//     }
//
//     @GetMapping("/employees")
//     public List<Employee> getEmployees() {
//         return employeeService.findAll();
//     }
//
//     @GetMapping("/employees/{employeeID}")
//     public Employee getEmployeeByID(@PathVariable int employeeID) {
//         Employee employee = employeeService.findById(employeeID);
//
//         if (employee == null) {
//             throw new RuntimeException("Could not find employee with id: " + employeeID);
//         }
//
//         return employee;
//     }
//
//     @PostMapping("/employees")
//     public String addEmployee(@RequestBody Employee employee) {
//         employee.setId(0);
//         employeeService.save(employee);
//
//         return "Added employee: " + employee;
//     }
//
//     @PutMapping("employees")
//     public String updateEmployee(@RequestBody Employee employee) {
//         employeeService.save(employee);
//
//         return "Updated employee: " + employee;
//     }
//
//     @DeleteMapping("/employees/{employeeID}")
//     public String deleteEmployeeByID(@PathVariable int employeeID) {
//         if (employeeService.findById(employeeID) == null) {
//             throw new RuntimeException("Could not find employee with id: " + employeeID);
//         }
//
//         employeeService.deleteById(employeeID);
//
//         return "Deleted employee with id: " + employeeID;
//     }
// }
