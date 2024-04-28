package com.tmquang.score.repositories;

import org.springframework.data.jpa.repository.*;

import com.tmquang.score.models.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
