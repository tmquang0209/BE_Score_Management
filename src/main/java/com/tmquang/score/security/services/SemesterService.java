package com.tmquang.score.security.services;

import com.tmquang.score.models.Semester;
import com.tmquang.score.repositories.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SemesterService {
    @Autowired
    SemesterRepository semesterRepository;

    public Semester saveSemester(Semester semester){
        return semesterRepository.save(semester);
    }

    public List<Semester> getAll(){
        return semesterRepository.findAll();
    }

    public Semester getById(Integer id){
        return semesterRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Cannot find semester with id: " + id ));
    }

    public Semester update(Integer id, Semester data){
        Optional<Semester> findSemesterOptional = semesterRepository.findById(id);

        if(findSemesterOptional.isPresent()){
            Semester findSemester = findSemesterOptional.get();
            findSemester.setSemester(data.getSemester());
            findSemester.setYear(data.getYear());

            return semesterRepository.save(findSemester);
        } else {
            return null;
        }
    }

    public void delete(Integer id){
        semesterRepository.deleteById(id);
    }
}
