package com.tmquang.score.controllers;

import com.tmquang.score.models.Enrollment;
import com.tmquang.score.security.services.EnrollmentService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping("/create")
    public ApiResponse<Enrollment> create(@RequestBody Enrollment enrollment) {
        try {
            enrollmentService.saveEnrollment(enrollment);
            return new ApiResponse<>(true, null, "Enrollment created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Enrollment> getAll(){
        try{
            List<Enrollment> enrollmentList = enrollmentService.getAll();
            return new ApiResponse<>(true, enrollmentList, "Get enrollment list successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Enrollment> getById(@PathVariable Integer id){
        try{
            Enrollment enrollmentDetails = enrollmentService.getById(id);
            return new ApiResponse<>(true, List.of(enrollmentDetails), "Get enrollment details successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Enrollment> updatebyId(@PathVariable Integer id, @RequestBody Enrollment enrollment){
        try{
            enrollmentService.update(id, enrollment);
            return new ApiResponse<>(true, null, "Enrollment updated successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Enrollment> deleteById(@PathVariable Integer id){
        try{
            enrollmentService.delete(id);
            return new ApiResponse<>(true, null, "Enrollment deleted successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
