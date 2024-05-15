package com.tmquang.score.controllers;

import com.tmquang.score.models.Year;
import com.tmquang.score.security.services.YearService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/year")
public class YearController {
    @Autowired
    YearService yearService;

    @PostMapping("/create")
    public ApiResponse<Year> create(@RequestBody Year yearRequest) {
        Year newYear = yearService.saveYear(yearRequest);
        return new ApiResponse<>(true, List.of(newYear), "Year created successfully.");
    }

    @GetMapping("/all")
    public ApiResponse<Year> getAllYear() {
        List<Year> yearList = yearService.getAll();

        return new ApiResponse<>(true, yearList, "Get year successfully.");
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Year> getById(@PathVariable Integer id){
        try {
            Year details = yearService.getById(id);
            return new ApiResponse<>(true, List.of(details), "Get detail successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, "Error: Cannot find year with id: " + id);
        }
    }


    @PutMapping("/update/{id}")
    public ApiResponse<Year> updateDetails(@PathVariable Integer id, @RequestBody Year data){
        Year update = yearService.update(id, data);
        return new ApiResponse<Year>(true, List.of(update), "Update successful");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteYearById(@PathVariable Integer id){
        yearService.delete(id);
        return new ApiResponse<Object>(true, null, "Delete year successful.");
    }
}
