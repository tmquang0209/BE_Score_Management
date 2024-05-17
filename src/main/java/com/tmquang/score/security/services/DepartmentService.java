package com.tmquang.score.security.services;

import com.tmquang.score.models.Department;
import com.tmquang.score.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Integer id){
        return departmentRepository.findById(id);
    }

    public Department saveDepartment(Department department) throws Exception {
        // check data
        Optional<Department> isExists = departmentRepository.findByCode(department.getCode());
        if(isExists.isPresent()) {
            throw new Exception("Department with code "+ department.getCode() +" is exists");
        }
        departmentRepository.save(department);
        return department;
    }

    public Department updateDepartment(Integer id, Department department) {
        Optional<Department> existingDepartmentOptional = departmentRepository.findById(id);
        if (existingDepartmentOptional.isPresent()) {
            Department existingDepartment = existingDepartmentOptional.get();
            existingDepartment.setCode(department.getCode());
            existingDepartment.setName(department.getName());
            return departmentRepository.save(existingDepartment);
        } else {
            // Handle not found scenario
            return null;
        }
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }

}
