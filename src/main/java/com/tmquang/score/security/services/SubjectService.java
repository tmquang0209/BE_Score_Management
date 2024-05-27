package com.tmquang.score.security.services;

import com.tmquang.score.models.Subject;
import com.tmquang.score.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    public Subject saveSubject(Subject subject){
        subjectRepository.save(subject);
        return subject;
    }

    public List<Subject> getAll(){
        return subjectRepository.findAll();
    }

    public Subject getById(Integer id){
        return subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Cannot find subject with id: " + id ));
    }

    public Subject update(Integer id, Subject data){
        Optional<Subject> findSubjectOptional = subjectRepository.findById(id);

        if(findSubjectOptional.isPresent()){
            Subject findSubject = findSubjectOptional.get();
            findSubject.setSubjectCode(data.getSubjectCode());
            findSubject.setSubjectName(data.getSubjectName());
            findSubject.setCredit(data.getCredit());
            findSubject.setRate(data.getRate());

            return subjectRepository.save(findSubject);
        } else {
            return null;
        }
    }

    public List<Subject> searchKeyword(String keyword){
        System.out.println(keyword);
        return subjectRepository.findByKeyword(keyword);
    }

    public void delete(Integer id){
        subjectRepository.deleteById(id);
    }
}
