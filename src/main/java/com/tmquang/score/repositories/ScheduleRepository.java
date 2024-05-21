package com.tmquang.score.repositories;

import com.tmquang.score.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findBySemesterId(Integer id);
    List<Schedule> findByClassName(String className);

    @Query("SELECT s FROM Schedule s WHERE s.semester.id = :semesterId " +
            "AND (:className IS NULL OR :className = '' OR s.className LIKE %:className%) " +
            "AND (:subjectId IS NULL OR s.subject.id = :subjectId)")
    List<Schedule> findBySemesterAndClassNameAndSubjectId(@Param("semesterId") Integer semesterId, @Param("className") String className, @Param("subjectId") Integer subjectId);
}
