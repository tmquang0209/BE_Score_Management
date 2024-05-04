package com.tmquang.score.security.services;

import com.tmquang.score.models.Teacher;
import com.tmquang.score.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements UserDetailsService {
    @Autowired
    TeacherRepository teacherRepository;

    public Teacher saveTeacher(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String teacherCode) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByTeacherCode(teacherCode).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + teacherCode));
        return TeacherImpl.build(teacher);
    }
}
