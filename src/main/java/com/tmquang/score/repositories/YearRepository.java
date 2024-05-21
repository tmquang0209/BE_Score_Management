package com.tmquang.score.repositories;

import com.tmquang.score.models.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearRepository extends JpaRepository<Year, Integer> {
    Optional<Year> findByYear(String year);

//    @Query("SELECT * FROM schedules s JOIN ")
//    List<Year> getYearAndSemester();
}
