package com.tmquang.score.controllers;

import com.tmquang.score.models.Student;
import com.tmquang.score.security.services.StudentService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ApiResponse<Student> create(@RequestBody Student student) {
        try {
            // check student
            Student checkStudent = studentService.getByCode(student.getStudentCode());
            if(checkStudent != null)
                throw new RuntimeException("Student exists with code: " + student.getStudentCode());

            String hashPassword = passwordEncoder.encode(student.getPassword());
            student.setPassword(hashPassword);

            Student newStudent = studentService.saveStudent(student);
            return new ApiResponse<>(true, List.of(newStudent), "Student created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Student> getAll() {
        try {
            List<Student> studentList = studentService.getAll();
            return new ApiResponse<>(true, studentList, "Get student list successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Student> getById(@PathVariable Integer id) {
        try {
            Student studentDetails = studentService.getById(id);
            return new ApiResponse<>(true, List.of(studentDetails), "Get student details successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/studentCode/{studentCode}")
    public ApiResponse<Student> getByCode(@PathVariable String studentCode) {
        try {
            Student studentDetails = studentService.getByCode(studentCode);
            return new ApiResponse<>(true, List.of(studentDetails), "Get student details successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Student> updateById(@PathVariable Integer id, @RequestBody Student student) {
        try {
            Student update = studentService.update(id, student);
            return new ApiResponse<>(true, List.of(update), "Student updated successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Student> deleteById(@PathVariable Integer id) {
        try {
            studentService.delete(id);
            return new ApiResponse<>(true, null, "Student deleted successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
