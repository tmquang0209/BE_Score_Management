package com.tmquang.score.repositories;

import com.tmquang.score.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Optional<Schedule> findBySemesterId(Integer id);
}
