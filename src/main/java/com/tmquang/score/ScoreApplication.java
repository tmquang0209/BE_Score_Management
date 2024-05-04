package com.tmquang.score;

import com.tmquang.score.enums.ERole;
import com.tmquang.score.models.*;
import com.tmquang.score.repositories.EmployeeRepository;
import com.tmquang.score.repositories.MajorRepository;
import com.tmquang.score.repositories.RoleRepository;
import com.tmquang.score.repositories.TeacherRepository;
import com.tmquang.score.security.services.EmployeeServiceImpl;
import com.tmquang.score.security.services.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
public class ScoreApplication {

	private final RoleRepository roleRepository;
	private final MajorRepository majorRepository;
	private final TeacherRepository teacherRepository;
	private final EmployeeRepository employeeRepository;

	private final EmployeeServiceImpl employeeService;
	private final TeacherServiceImpl teacherService;

	@Autowired
	public ScoreApplication(RoleRepository roleRepository, MajorRepository majorRepository, TeacherRepository teacherRepository, EmployeeRepository employeeRepository, EmployeeServiceImpl employeeService, TeacherServiceImpl teacherService) {
		this.roleRepository = roleRepository;
        this.majorRepository = majorRepository;
        this.teacherRepository = teacherRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.teacherService = teacherService;
    }

	public static void main(String[] args) {
		SpringApplication.run(ScoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder) {
		return args -> {
			Role empRole = roleRepository.findByName(ERole.ADMIN).orElseThrow(()-> new RuntimeException("Error: role not found"));

			Employee admin = new Employee("PC002", "Nguyen Van A", "01233333", "abcd1@gmail.com", passwordEncoder.encode("123456"), new Date(1990, 1, 1), "male", empRole);
			if(employeeRepository.findByCode("PC002").isEmpty())
				employeeService.saveEmployee(admin);

			Major major = majorRepository.findByMajorCode("TT").orElseThrow(() -> new RuntimeException(("Error: major not found")));

			Teacher teacher = new Teacher("CTI001", "Tran Van Manh", passwordEncoder.encode("123456"), "cti001@gmail.com", "01234566666", "Ha Noi", new Date(1992,2,3), "male", major);
			if(teacherRepository.findByTeacherCode("CTI001").isEmpty()){
				teacherService.saveTeacher(teacher);
			}
		};
	}
}
