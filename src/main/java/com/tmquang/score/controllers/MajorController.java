package com.tmquang.score.controllers;

import com.tmquang.score.models.Major;
import com.tmquang.score.payload.request.MajorRequest;
import com.tmquang.score.security.services.MajorService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/major")
public class MajorController {
    @Autowired
    MajorService majorService;

    @PostMapping("/create")
    public ApiResponse<Major> create(@RequestBody Major major) {
        try {
            Major create = majorService.saveMajor(major);
            return new ApiResponse<>(true, List.of(create), "Major created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Major> getAll() {
        try {
            List<Major> majorList = majorService.getAll();
            return new ApiResponse<>(true, majorList, "Get major list successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Major> getById(@PathVariable Integer id) {
        try {
            Major majorDetails = majorService.getById(id);
            return new ApiResponse<>(true, List.of(majorDetails), "Get major details successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Major> updateById(@PathVariable Integer id, @RequestBody MajorRequest major) {
        try {
            Major update = majorService.update(id, major);
            return new ApiResponse<>(true, List.of(update), "Major updated successfully.");
        } catch (NumberFormatException e) {
            return new ApiResponse<>(false, null, "Invalid ID format. ID must be an integer.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse<Major> deleteById(@PathVariable Integer id) {
        try {
            majorService.delete(id);
            return new ApiResponse<>(true, null, "Major deleted successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
