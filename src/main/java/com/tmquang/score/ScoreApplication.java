package com.tmquang.score;

import com.tmquang.score.enums.ERole;
import com.tmquang.score.models.Department;
import com.tmquang.score.models.Employee;
import com.tmquang.score.models.Role;
import com.tmquang.score.repositories.RoleRepository;
import com.tmquang.score.security.services.EmployeeServiceImpl;
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
	private final EmployeeServiceImpl employeeService;

	@Autowired
	public ScoreApplication(RoleRepository roleRepository, EmployeeServiceImpl employeeService) {
		this.roleRepository = roleRepository;
        this.employeeService = employeeService;
    }

	public static void main(String[] args) {
		SpringApplication.run(ScoreApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder) {
//		return args -> {
//			Role empRole = roleRepository.findByName(ERole.MANAGER).orElseThrow(()-> new RuntimeException("Error: role not found"));
//
//			Employee admin = new Employee("PC001", "Nguyen Van B", "01233333", "abcd@gmail.com", passwordEncoder.encode("123456"), new Date(1990, 1, 1), "male", empRole);
//			employeeService.saveEmployee(admin);
//
//			// You might want to save the admin employee using a service or repository
//		};
//	}
}
