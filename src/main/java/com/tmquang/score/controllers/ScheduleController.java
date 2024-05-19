package com.tmquang.score.controllers;

import com.tmquang.score.models.Schedule;
import com.tmquang.score.security.services.ScheduleService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/create")
    public ApiResponse<Schedule> create(@RequestBody Schedule schedule) {
        try {
            scheduleService.saveSchedule(schedule);
            return new ApiResponse<>(true, null, "Schedule created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Schedule> getAll(){
        try{
            List<Schedule> scheduleList = scheduleService.getAll();
            return new ApiResponse<>(true, scheduleList, "Get schedule list successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Schedule> getById(@PathVariable Integer id){
        try{
            Schedule scheduleDetails = scheduleService.getById(id);
            return new ApiResponse<>(true, List.of(scheduleDetails), "Get schedule details successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Schedule> updatebyId(@PathVariable Integer id, @RequestBody Schedule schedule){
        try{
            Schedule update = scheduleService.update(id, schedule);
            return new ApiResponse<>(true, List.of(update), "Schedule updated successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Schedule> deleteById(@PathVariable Integer id){
        try{
            scheduleService.delete(id);
            return new ApiResponse<>(true, null, "Schedule deleted successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
