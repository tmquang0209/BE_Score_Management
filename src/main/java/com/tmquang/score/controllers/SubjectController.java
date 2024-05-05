package com.tmquang.score.controllers;

import com.tmquang.score.models.Subject;
import com.tmquang.score.security.services.SubjectService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @PostMapping("/create")
    public ApiResponse<Subject> create(@RequestBody Subject subject) {
        try {
            subjectService.saveSubject(subject);
            return new ApiResponse<>(true, null, "Subject created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Subject> getAll(){
        try{
            List<Subject> subjectList = subjectService.getAll();
            return new ApiResponse<>(true, subjectList, "Get subject list successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Subject> getById(@PathVariable Integer id){
        try{
            Subject subjectDetails = subjectService.getById(id);
            return new ApiResponse<>(true, List.of(subjectDetails), "Get subject details successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Subject> updatebyId(@PathVariable Integer id, @RequestBody Subject subject){
        try{
            subjectService.update(id, subject);
            return new ApiResponse<>(true, null, "Subject updated successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Subject> deleteById(@PathVariable Integer id){
        try{
            subjectService.delete(id);
            return new ApiResponse<>(true, null, "Subject deleted successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
