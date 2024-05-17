package com.tmquang.score.controllers;

import com.tmquang.score.models.Teacher;
import com.tmquang.score.security.services.TeacherServiceImpl;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ApiResponse<Teacher> create(@RequestBody Teacher teacher) {
        try {
            // check user exists
            Teacher checkTeacher = teacherService.getByTeacherCode(teacher.getTeacherCode());
            System.out.println(checkTeacher);
            if(checkTeacher != null)
                throw new RuntimeException("Teacher exists with code: " + teacher.getTeacherCode());

            // hash password
            String hashPassword = passwordEncoder.encode(teacher.getPassword());
            teacher.setPassword(hashPassword);
            teacherService.saveTeacher(teacher);
            return new ApiResponse<>(true, null, "Teacher created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Teacher> getAll() {
        try {
            List<Teacher> teacherList = teacherService.getAll();
            return new ApiResponse<>(true, teacherList, "Get teacher list successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Teacher> getById(@PathVariable Integer id) {
        try {
            Teacher teacherDetails = teacherService.getById(id);
            return new ApiResponse<>(true, List.of(teacherDetails), "Get teacher details successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Teacher> updateById(@PathVariable Integer id, @RequestBody Teacher teacher) {
        try {
            Teacher checkTeacher = teacherService.getById(id);
            if (checkTeacher == null)
                throw new RuntimeException("Can not find teacher with id: " + id);
            teacherService.update(id, teacher);
            Teacher teacherDetails = teacherService.getById(id);
            return new ApiResponse<>(true, List.of(teacherDetails), "Teacher updated successfully.");
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

//    @DeleteMapping("/delete/{id}")
//    public ApiResponse<Teacher> deleteById(@PathVariable Integer id) {
//        try {
//            teacherService.delete(id);
//            return new ApiResponse<>(true, null, "Teacher deleted successfully.");
//        } catch (Exception e) {
//            return new ApiResponse<>(false, null, e.getMessage());
//        }
//    }
}
