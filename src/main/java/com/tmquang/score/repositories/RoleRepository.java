package com.tmquang.score.repositories;

import com.tmquang.score.enums.ERole;
import com.tmquang.score.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
    Optional<Role> findById(Integer id);
}
