package com.tmquang.score.controllers;

import com.tmquang.score.models.Enrollment;
import com.tmquang.score.models.Schedule;
import com.tmquang.score.models.Student;
import com.tmquang.score.payload.request.EnrollmentRequest;
import com.tmquang.score.security.services.EnrollmentService;
import com.tmquang.score.security.services.ScheduleService;
import com.tmquang.score.security.services.StudentService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    StudentService studentService;

    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping("/add")
    public ApiResponse<Student> create(@RequestBody EnrollmentRequest enrollment) {
        try {
            System.out.println(enrollment.getScheduleId() + ", " + enrollment.getStudentId());
            if (enrollment.getStudentId() == null) {
                throw new RuntimeException("Student code is required.");
            }

            if (enrollment.getScheduleId() == null) {
                throw new RuntimeException("Schedule id is required.");
            }

            Schedule schedule = scheduleService.getById(enrollment.getScheduleId());
            Student student = studentService.getByCode(enrollment.getStudentId());
            
            Enrollment newEnrollment = new Enrollment(student, schedule);

            Enrollment saveEnrollment = enrollmentService.saveEnrollment(newEnrollment);
            return new ApiResponse<>(true, List.of(student), "Enrollment created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Enrollment> getAll() {
        try {
            List<Enrollment> enrollmentList = enrollmentService.getAll();
            return new ApiResponse<>(true, enrollmentList, "Get enrollment list successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PostMapping("/search")
    public ApiResponse<Student> search(@RequestBody EnrollmentRequest data) {
        try {
            List<Student> enrollments = enrollmentService.getByScheduleId(data.getScheduleId());
            return new ApiResponse<>(true, enrollments, "Get enrollment records successful.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Enrollment> getById(@PathVariable Integer id) {
        try {
            Enrollment enrollmentDetails = enrollmentService.getById(id);
            return new ApiResponse<>(true, List.of(enrollmentDetails), "Get enrollment details successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Enrollment> updatebyId(@PathVariable Integer id, @RequestBody Enrollment enrollment) {
        try {
            enrollmentService.update(id, enrollment);
            return new ApiResponse<>(true, null, "Enrollment updated successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{scheduleId}/{studentId}")
    public ApiResponse<Enrollment> deleteById(@PathVariable Integer scheduleId, @PathVariable Integer studentId) {
        try {
            System.out.println("Schedule id: " + scheduleId);
            System.out.println("Student id: " + studentId);

            Enrollment findEnroll = enrollmentService.getByScheduleAndStudent(scheduleId, studentId);
            if(findEnroll == null)
                throw new Exception("Can not find enrollment");

            enrollmentService.delete(findEnroll.getId());
            return new ApiResponse<>(true, null, "Enrollment deleted successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

}
