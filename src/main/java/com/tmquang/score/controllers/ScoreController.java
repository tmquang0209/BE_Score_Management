package com.tmquang.score.controllers;

import com.tmquang.score.dto.StudentScoreDTO;
import com.tmquang.score.models.Score;
import com.tmquang.score.models.Semester;
import com.tmquang.score.models.Student;
import com.tmquang.score.models.Subject;
import com.tmquang.score.payload.request.ScoreRequest;
import com.tmquang.score.security.services.ScoreService;
import com.tmquang.score.security.services.SemesterService;
import com.tmquang.score.security.services.StudentService;
import com.tmquang.score.security.services.SubjectService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    SemesterService semesterService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    StudentService studentService;

    @Autowired
    ScoreService scoreService;

    @PostMapping("/create/{semesterId}/{subjectId}")
    public ApiResponse<Score> create(@PathVariable Integer semesterId, @PathVariable Integer subjectId, @RequestBody List<ScoreRequest> scores) {
        try {
            Semester semester = semesterService.getById(semesterId);
            Subject subject = subjectService.getById(subjectId);

            for (ScoreRequest score : scores) {
                Student student = studentService.getByCode(score.getStudentCode());
                System.out.println(
                        "Semester ID: " + semester.getId() +
                                ", Subject ID: " + subject.getId() +
                                ", Student ID: " + student.getId() +
                                ", Midterm Test Score: " + score.getMidtermTest() +
                                ", Final Test Score: " + score.getFinalTest()
                );

                float scoreCalc = 0;
                if (score.getFinalTest() != 0) {
                    float rate = (subject.getRate() > 1) ? (subject.getRate() / 100) : subject.getRate();
                    scoreCalc = score.getFinalTest() * rate + score.getMidtermTest() * (1 - rate);
                }

                Score newScore = new Score(semester, subject, student, score.getMidtermTest(), score.getFinalTest(), scoreCalc);
                scoreService.saveScore(newScore);
            }
            return new ApiResponse<>(true, null, "Score created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @PostMapping("/addList")
    public ApiResponse<Score> addList(@RequestBody List<Score> scores) {
        return new ApiResponse<>(true, null, "");
    }

    @GetMapping("/all")
    public ApiResponse<Score> getAll() {
        try {
            List<Score> scoreList = scoreService.getAll();
            return new ApiResponse<>(true, scoreList, "Get score list successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/schedule/{id}")
    public ApiResponse<StudentScoreDTO> getByScheduleId(@PathVariable Integer id) {
        List<StudentScoreDTO> studentScoreDTOS = scoreService.getStudentScoresByScheduleId(id);

        return new ApiResponse<>(true, studentScoreDTOS, "Get scores list successful");
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Score> getById(@PathVariable Integer id) {
        try {
            Score scoreDetails = scoreService.getById(id);
            return new ApiResponse<>(true, List.of(scoreDetails), "Get score details successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/schedules/{scheduleId}/{studentId}")
    public ApiResponse<StudentScoreDTO> getStudentScoresByStudentIdAndScheduleId(@PathVariable Integer studentId, @PathVariable Integer scheduleId) {
        try {
            StudentScoreDTO studentScore = scoreService.getStudentScoresByStudentIdAndScheduleId(studentId, scheduleId);
            if (studentScore == null) {
                throw new RuntimeException("Can not find score");
            }
            return new ApiResponse<>(true, List.of(studentScore), "Get score successful,");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{scheduleId}")
    public ApiResponse<Score> updateById(@PathVariable Integer scheduleId, @RequestBody ScoreRequest score) {
        try {
            System.out.println("Schedule id:" + scheduleId +
                    "\t student code: " + score.getStudentCode() + "\t midterm: " + score.getMidtermTest() + "\t final: " + score.getFinalTest());
            Student student = studentService.getByCode(score.getStudentCode());

            StudentScoreDTO studentScoreDTO = scoreService.getStudentScoresByStudentIdAndScheduleId(student.getId(), scheduleId);
            if(studentScoreDTO == null)
                throw new RuntimeException("Can not find record");

            int rowsAffected = scoreService.updateScoreByScheduleIdAndStudentCode(scheduleId, score.getStudentCode(), score.getMidtermTest(), score.getFinalTest());
            if (rowsAffected > 0) {
                return new ApiResponse<>(true, null, "Score updated successfully.");
            } else {
                return new ApiResponse<>(true, null, "Failed to update score.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Score> deleteById(@PathVariable Integer id) {
        try {
            scoreService.delete(id);
            return new ApiResponse<>(true, null, "Score deleted successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
