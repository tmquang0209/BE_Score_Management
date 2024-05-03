package com.tmquang.score.controllers;

import java.util.List;
import java.util.Optional;

import com.tmquang.score.security.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmquang.score.models.Department;
import com.tmquang.score.utils.ApiResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController("/department")
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService; // Initialize department object

    // get all departments
    @GetMapping("/all")
    public ApiResponse<Department> getAllDepartments() {
        List<Department> departments = (List<Department>) departmentService.getAllDepartments();
        boolean success = departments != null; // Check if department retrieval was successful
        String message = success ? "Departments retrieved successfully." : "Failed to retrieve departments.";
        return new ApiResponse<>(success, departments, message);
    }

    // get department by id
    @GetMapping("/getById/{id}")
    public ApiResponse<Optional<Department>> getDepartmentById(@PathVariable Integer id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        boolean success = department != null; // Check if department retrieval was successful
        String message = success ? "Department retrieved successfully." : "Failed to retrieve department.";
        return new ApiResponse<>(success, List.of(department), message);
    }

    // create department
    @PostMapping("/create")
    public ApiResponse<Department> newProduct(@RequestBody Department department) {
        departmentService.saveDepartment(department);
        return new ApiResponse<>(true, List.of(department), "Department created successfully.");
    }

    // Update department
    @PutMapping("/update/{id}")
    public ApiResponse<Department> updateDepartment(@RequestHeader("Authorization") String token,
           @PathVariable Integer id, @RequestBody Department department) {
        Department updatedDepartment = departmentService.updateDepartment(id, department);
        boolean success = updatedDepartment != null; // Check if department update was successful
        String message = success ? "Department updated successfully." : "Failed to update department.";
        return new ApiResponse<>(success, List.of(updatedDepartment), message);
    }

    // Delete department
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Department> deleteDepartment(@RequestHeader("Authorization") String token,
            @PathVariable Integer id) {
        departmentService.deleteDepartment(id);
        return new ApiResponse<>(true, null, "Department deleted successfully.");
    }
}
