package com.tmquang.score.security.services;

import com.tmquang.score.models.Student;
import com.tmquang.score.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements UserDetailsService {
    @Autowired
    StudentRepository studentRepository;

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getAll(){
        return studentRepository.findAll();
    }

    public Student getById(Integer id){
        return studentRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Student not found with id: " + id));
    }

    public Student getByCode(String code){
        return studentRepository.findByStudentCode(code).orElse(null);
    }

    public Student update(Integer id, Student data){
        Optional<Student> findStudentOptional = studentRepository.findById(id);

        if(findStudentOptional.isPresent()){
            Student findStudent = findStudentOptional.get();
            findStudent.setStudentCode(data.getStudentCode());
            findStudent.setName(data.getName());
            findStudent.setEmail(data.getEmail());
            findStudent.setGender(data.getGender());
            findStudent.setAddress(data.getAddress());
            findStudent.setDob(data.getDob());
            findStudent.setPhone(data.getPhone());
            findStudent.setMajor(data.getMajor());

            return studentRepository.save(findStudent);
        } else {
            throw new UsernameNotFoundException("Student not found with id: " + id);
        }
    }

    public void delete(Integer id){
        studentRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String studentCode) throws UsernameNotFoundException {
        Student student = studentRepository.findByStudentCode(studentCode).orElseThrow(() -> new UsernameNotFoundException("Student not found with username: " + studentCode));
        return StudentImpl.build(student);
    }
}
