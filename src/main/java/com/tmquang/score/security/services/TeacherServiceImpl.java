package com.tmquang.score.security.services;

import com.tmquang.score.models.Employee;
import com.tmquang.score.models.Major;
import com.tmquang.score.models.Teacher;
import com.tmquang.score.payload.request.UserRequest;
import com.tmquang.score.repositories.MajorRepository;
import com.tmquang.score.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements UserDetailsService {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    MajorRepository majorRepository;

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public Teacher saveTeacher(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public Teacher getById(Integer id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Teacher getByTeacherCode(String code){
        return teacherRepository.findByTeacherCode(code).orElse(null);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String teacherCode) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByTeacherCode(teacherCode).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + teacherCode));
        return TeacherImpl.build(teacher);
    }

    public Teacher update(Integer id, Teacher data){
        Optional<Teacher> findTeacherOptional = teacherRepository.findById(id);
        if(findTeacherOptional.isPresent()){
            Teacher findTeacher = findTeacherOptional.get();
//            Major major = majorRepository.getReferenceById(data.getMajor().getId());
//            data.setMajor(major);
            findTeacher.setInfo(data);
            return teacherRepository.save(findTeacher);
        }
        return null;
    }

    public Teacher update(Integer id, UserRequest data){
        Optional<Teacher> findTeacherOptional = teacherRepository.findById(id);
        if(findTeacherOptional.isPresent()){
            Teacher findTeacher = findTeacherOptional.get();
            findTeacher.setEmail(data.getEmail());
            findTeacher.setPhone(data.getPhone());
            findTeacher.setAddress(data.getAddress());
            findTeacher.setDob(data.getDob());
            return teacherRepository.save(findTeacher);
        }
        return null;
    }

    public Teacher changePassword(Integer id, String newPassword){
        Optional<Teacher> findTeacherOptional = teacherRepository.findById(id);
        if(findTeacherOptional.isPresent()){
            Teacher findTeacher = findTeacherOptional.get();
            findTeacher.setPassword(newPassword);
            return teacherRepository.save(findTeacher);
        }
        return null;
    }
}
