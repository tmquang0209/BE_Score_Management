package com.tmquang.score.controllers;

import com.tmquang.score.models.Employee;
import com.tmquang.score.payload.request.UserRequest;
import com.tmquang.score.security.services.EmployeeServiceImpl;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ApiResponse<Employee> create(@RequestBody Employee employee) {
        try {
            String password = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(password);
            
            Employee newEmp = employeeService.saveEmployee(employee);
            return new ApiResponse<>(true, List.of(newEmp), "Employee created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Employee> getAll() {
        try {
            List<Employee> employeeList = employeeService.getAll();
            return new ApiResponse<>(true, employeeList, "Get employee list successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Employee> getById(@PathVariable Integer id) {
        try {
            Employee employeeDetails = employeeService.getById(id);
            return new ApiResponse<>(true, List.of(employeeDetails), "Get employee details successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Employee> updateById(@PathVariable Integer id, @RequestBody Employee employee) {
        try {
            Employee update = employeeService.update(id, employee);
            return new ApiResponse<>(true, List.of(update), "Employee updated successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Employee> deleteById(@PathVariable Integer id) {
        try {
            employeeService.delete(id);
            return new ApiResponse<>(true, null, "Employee deleted successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
