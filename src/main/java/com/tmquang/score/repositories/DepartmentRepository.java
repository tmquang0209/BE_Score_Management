package com.tmquang.score.repositories;

import org.springframework.data.jpa.repository.*;

import com.tmquang.score.models.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByCode(String code);
}
