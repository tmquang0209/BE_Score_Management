package com.tmquang.score.repositories;

import com.tmquang.score.models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {
    @Query("SELECT s FROM Semester s WHERE s.year.id = :yearId")
    List<Semester> findByYear(@Param("yearId") Integer yearId);
}
