package com.tmquang.score.security.services;

import com.tmquang.score.models.Enrollment;
import com.tmquang.score.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    @Autowired
    EnrollmentRepository enrollmentRepository;

    public void saveEnrollment(Enrollment enrollment){
        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAll(){
        return enrollmentRepository.findAll();
    }

    public Enrollment getById(Integer id){
        return enrollmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Cannot find enrollment with id: " + id ));
    }

    public Enrollment update(Integer id, Enrollment data){
        Optional<Enrollment> findEnrollmentOptional = enrollmentRepository.findById(id);

        if(findEnrollmentOptional.isPresent()){
            Enrollment findEnrollment = findEnrollmentOptional.get();
            findEnrollment.setSchedule(data.getSchedule());
            findEnrollment.setSemester(data.getSemester());
            findEnrollment.setStudent(data.getStudent());

            return enrollmentRepository.save(findEnrollment);
        } else {
            return null;
        }
    }

    public void delete(Integer id){
        enrollmentRepository.deleteById(id);
    }
}
