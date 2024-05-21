package com.tmquang.score.repositories;

import com.tmquang.score.models.Enrollment;
import com.tmquang.score.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    Optional<Enrollment> getByStudentId(Integer id);

    @Query("SELECT e.student FROM Enrollment e WHERE e.schedule.id = :scheduleId")
    List<Student> getByScheduleId(@Param("scheduleId") Integer scheduleId);

    @Query("SELECT e FROM Enrollment e WHERE e.schedule.id = :scheduleId AND e.student.id = :studentId")
    Optional<Enrollment> getByScheduleAndStudent(@Param("scheduleId") Integer scheduleId, @Param("studentId") Integer studentId);
}
