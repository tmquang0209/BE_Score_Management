package com.tmquang.score.repositories;

import com.tmquang.score.models.Semester;
import com.tmquang.score.models.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {
    Optional<Semester> findByYear(Year year);
}
