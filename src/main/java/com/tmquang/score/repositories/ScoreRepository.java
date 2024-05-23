package com.tmquang.score.repositories;

import com.tmquang.score.dto.StudentScoreDTO;
import com.tmquang.score.models.Score;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    Optional<Score> findByStudentId(Integer id);

    @Query("SELECT new com.tmquang.score.dto.StudentScoreDTO(st.studentCode, st.name, sc.midtermScore, sc.finalScore, sc.score) " +
            "FROM Enrollment e " +
            "JOIN e.student st " +
            "JOIN Score sc ON sc.student.id = st.id " +
            "JOIN e.schedule sch " +
            "WHERE sch.id = :scheduleId " +
            "AND sc.subject.id = sch.subject.id " +
            "AND sc.semester.id = sch.semester.id")
    List<StudentScoreDTO> findStudentScoresByScheduleId(@Param("scheduleId") Integer scheduleId);

    @Query("SELECT new com.tmquang.score.dto.StudentScoreDTO(st.studentCode, st.name, sc.midtermScore, sc.finalScore, sc.score) " +
            "FROM Enrollment e " +
            "JOIN e.student st " +
            "JOIN Score sc ON sc.student.id = st.id " +
            "JOIN e.schedule sch " +
            "WHERE sch.id = :scheduleId " +
            "AND st.id = :studentId " +
            "AND sc.subject.id = sch.subject.id " +
            "AND sc.semester.id = sch.semester.id")
    Optional<StudentScoreDTO> findStudentScoresByStudentIdAndScheduleId(@Param("studentId") Integer studentId, @Param("scheduleId") Integer scheduleId);

    @Modifying
    @Transactional
    @Query("UPDATE Score sc " +
            "SET sc.midtermScore = :midtermTest, " +
            "    sc.finalScore = :finalTest, " +
            "    sc.score = (:finalTest * CASE WHEN (SELECT s.rate FROM Subject s WHERE s.id = sc.subject.id) > 1 " +
            "                                   THEN (SELECT s.rate FROM Subject s WHERE s.id = sc.subject.id) / 100 " +
            "                                   ELSE (SELECT s.rate FROM Subject s WHERE s.id = sc.subject.id) END) + " +
            "               (:midtermTest * (1 - CASE WHEN (SELECT s.rate FROM Subject s WHERE s.id = sc.subject.id) > 1 " +
            "                                         THEN (SELECT s.rate FROM Subject s WHERE s.id = sc.subject.id) / 100 " +
            "                                         ELSE (SELECT s.rate FROM Subject s WHERE s.id = sc.subject.id) END)) " +
            "WHERE sc.student.id = (SELECT st.id FROM Student st WHERE st.studentCode = :studentCode) " +
            "  AND sc.subject.id = (SELECT sch.subject.id FROM Schedule sch WHERE sch.id = :scheduleId) " +
            "  AND sc.semester.id = (SELECT sch.semester.id FROM Schedule sch WHERE sch.id = :scheduleId)")
    int updateScoreByScheduleIdAndStudentCode(@Param("scheduleId") Integer scheduleId,
                                              @Param("studentCode") String studentCode,
                                              @Param("midtermTest") float midtermTest,
                                              @Param("finalTest") float finalTest);
}
