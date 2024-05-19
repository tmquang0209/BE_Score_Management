package com.tmquang.score.repositories;

import com.tmquang.score.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByCode(String code);
    Optional<Employee> findByCode(String code);
    Optional<Employee> findById(Integer id);
}
