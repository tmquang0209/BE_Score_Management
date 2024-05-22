package com.tmquang.score.security.services;

import com.tmquang.score.models.Enrollment;
import com.tmquang.score.models.Student;
import com.tmquang.score.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    @Autowired
    EnrollmentRepository enrollmentRepository;

    public Enrollment saveEnrollment(Enrollment enrollment){
        Enrollment checkEnroll = getByScheduleAndStudent(enrollment.getSchedule().getId(), enrollment.getStudent().getId());
        if(checkEnroll == null)
            return enrollmentRepository.save(enrollment);
        else
            throw new RuntimeException("Value is exists");
    }

    public List<Enrollment> getAll(){
        return enrollmentRepository.findAll();
    }

    public Enrollment getById(Integer id){
        return enrollmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Cannot find enrollment with id: " + id ));
    }

    public List<Student> getByScheduleId(Integer scheduleId){
        return enrollmentRepository.getByScheduleId(scheduleId);
    }

    public Enrollment getByScheduleAndStudent(Integer scheduleId, Integer studentId){
        return enrollmentRepository.getByScheduleAndStudent(scheduleId, studentId).orElse(null);
    }

    public Enrollment update(Integer id, Enrollment data){
        Optional<Enrollment> findEnrollmentOptional = enrollmentRepository.findById(id);

        if(findEnrollmentOptional.isPresent()){
            Enrollment findEnrollment = findEnrollmentOptional.get();
            findEnrollment.setSchedule(data.getSchedule());
//            findEnrollment.setSemester(data.getSemester());
            findEnrollment.setStudent(data.getStudent());

            return enrollmentRepository.save(findEnrollment);
        } else {
            return null;
        }
    }

    public void delete(Integer id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Enrollment not found with id: " + id);
        }
    }


}
