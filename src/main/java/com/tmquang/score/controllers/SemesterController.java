package com.tmquang.score.controllers;

import com.tmquang.score.models.Semester;
import com.tmquang.score.models.Year;
import com.tmquang.score.payload.request.SemesterRequest;
import com.tmquang.score.security.services.SemesterService;
import com.tmquang.score.security.services.YearService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semester")
public class SemesterController {
    @Autowired
    SemesterService semesterService;

    @Autowired
    YearService yearService;

    @PostMapping("/create")
    public ApiResponse<Semester> create(@RequestBody Semester data) {
        try {
            Semester newSemester = semesterService.saveSemester(data);
            return new ApiResponse<>(true, List.of(newSemester), "Semester created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Semester> getAll(){
        try{
            List<Semester> semesterList = semesterService.getAll();

            return new ApiResponse<>(true, semesterList, "Get semester list successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/year/{id}")
    public ApiResponse<Semester> getSemestersByYear(@PathVariable Integer id){
        try{
            List<Semester> semesterList = semesterService.getSemestersByYear(id);

            return new ApiResponse<>(true, semesterList, "Get semester list successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Semester> getById(@PathVariable Integer id){
        try{
            Semester semesterDetails = semesterService.getById(id);

            return new ApiResponse<>(true, List.of(semesterDetails), "Get semester list successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Semester> update(@PathVariable Integer id, @RequestBody Semester semesterRequest){
        try{
            Semester updateSemester = semesterService.update(id, semesterRequest);
            return new ApiResponse<>(true, List.of(updateSemester), "Semester update successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Semester> deleteById(@PathVariable Integer id){
        try{
            semesterService.delete(id);
            return new ApiResponse<>(true, null, "Semester delete successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
