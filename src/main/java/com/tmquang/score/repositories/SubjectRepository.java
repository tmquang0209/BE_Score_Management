package com.tmquang.score.repositories;

import com.tmquang.score.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findBySubjectCode(String code);

    @Query("SELECT s FROM Subject s WHERE s.subjectName LIKE %:name%")
    List<Subject> findByKeyword(@Param("name") String name);
}
