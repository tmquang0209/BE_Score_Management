package com.tmquang.score.security.services;

import com.tmquang.score.models.Employee;
import com.tmquang.score.models.Teacher;
import com.tmquang.score.repositories.EmployeeRepository;
import com.tmquang.score.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository, TeacherRepository teacherRepository) {
        this.employeeRepository = employeeRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the user exists in the Employee table
        Optional<Employee> employee = employeeRepository.findByCode(username);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            return new UserPrincipal(EmployeeImpl.build(emp), emp.getId(), emp.getEmail());
        }

        // Check if the user exists in the Teacher table
        Optional<Teacher> teacher = teacherRepository.findByTeacherCode(username);
        if (teacher.isPresent()) {
            Teacher teach = teacher.get();
            return new UserPrincipal(TeacherImpl.build(teach), teach.getId(), teach.getEmail());
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }

}
