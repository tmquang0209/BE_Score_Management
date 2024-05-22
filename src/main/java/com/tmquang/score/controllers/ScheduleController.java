package com.tmquang.score.controllers;

import com.tmquang.score.models.Employee;
import com.tmquang.score.models.Schedule;
import com.tmquang.score.models.Teacher;
import com.tmquang.score.payload.request.ScheduleRequest;
import com.tmquang.score.repositories.EmployeeRepository;
import com.tmquang.score.repositories.TeacherRepository;
import com.tmquang.score.security.jwt.JwtUtils;
import com.tmquang.score.security.services.ScheduleService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    JwtUtils jwtUtils;

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

    @PostMapping("/search")
    public ApiResponse<Schedule> search(@RequestBody ScheduleRequest data){
        try{
            List<Schedule> scheduleList;
            System.out.println(data.getSemesterId() + ", " + data.getClassName() + ", " + data.getSubjectId());
            if (data.getClassName() == null)
                data.setClassName("");

            scheduleList = scheduleService.findSchedules(data.getSemesterId(), data.getSubjectId(), data.getClassName());

            return new ApiResponse<>(true, scheduleList, "Get schedule list successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PostMapping("/getByTeacher")
    public ApiResponse<Schedule> getByTeacher(@RequestHeader(value = "Authorization") String bearerToken, @RequestBody ScheduleRequest data){
        try {
            String accessToken = bearerToken.replace("Bearer ", "");
            boolean isValidToken = jwtUtils.validateJwtToken(accessToken);
            if (isValidToken) {
                String username = jwtUtils.getUserNameFromJwtToken(accessToken);
                Employee empDetails = employeeRepository.findByCode(username).orElse(null);
                Teacher teacherDetails = teacherRepository.findByTeacherCode(username).orElse(null);

                if (empDetails != null) {
                    List<Schedule> scheduleList = scheduleService.getAll();

                    return new ApiResponse<>(true, scheduleList, "Get schedule successful.");
                } else if (teacherDetails != null) {
                    List<Schedule> scheduleList = scheduleService.findByTeacher(data.getSemesterId(), data.getSubjectId(), teacherDetails.getId());
                    return new ApiResponse<>(true, scheduleList, "Get schedule successful.");
                } else {
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }
            }
            return new ApiResponse<>(false, null, "Invalid token");
        } catch (UsernameNotFoundException e) {
            return new ApiResponse<>(false, null, e.getMessage());
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
