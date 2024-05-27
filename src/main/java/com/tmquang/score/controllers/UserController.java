package com.tmquang.score.controllers;

import com.tmquang.score.models.Employee;
import com.tmquang.score.models.Teacher;
import com.tmquang.score.payload.request.ChangePasswordRequest;
import com.tmquang.score.payload.request.LoginRequest;
import com.tmquang.score.payload.request.UserRequest;
import com.tmquang.score.payload.response.JwtResponse;
import com.tmquang.score.repositories.EmployeeRepository;
import com.tmquang.score.repositories.TeacherRepository;
import com.tmquang.score.security.jwt.JwtUtils;
import com.tmquang.score.security.services.EmployeeServiceImpl;
import com.tmquang.score.security.services.TeacherServiceImpl;
import com.tmquang.score.security.services.UserPrincipal;
import com.tmquang.score.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ApiResponse<Object> authenticateUser(@RequestBody LoginRequest loginRequest) {
        log.error(passwordEncoder.encode(loginRequest.getPassword()));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        String role = userDetails.getAuthorities().toString().replace('[', ' ').replace(']', ' ').trim();



        return new ApiResponse<Object>(true, List.of(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role)), "Login successful.");
    }

    @GetMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestHeader(value = "Authorization") String bearerToken) {
        String accessToken = bearerToken.replace("Bearer ", "");
        boolean isTokenValid = jwtUtils.validateJwtToken(accessToken);
        if (isTokenValid) {
            String username = jwtUtils.getUserNameFromJwtToken(accessToken);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.ok("User not authenticated");
            }

            String jwt = jwtUtils.generateJwtToken(authentication);
            System.out.println(jwt);

            return ResponseEntity.ok(new ApiResponse<>(true, List.of(jwt), "Token is valid for user: " + username));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Invalid token"));
        }
    }

    @GetMapping("/info")
    public ApiResponse<?> getInfo(@RequestHeader(value = "Authorization") String bearerToken) {
        try {
            String accessToken = bearerToken.replace("Bearer ", "");
            boolean isValidToken = jwtUtils.validateJwtToken(accessToken);
            if (isValidToken) {
                String username = jwtUtils.getUserNameFromJwtToken(accessToken);
                Employee empDetails = employeeRepository.findByCode(username).orElse(null);
                Teacher teacherDetails = teacherRepository.findByTeacherCode(username).orElse(null);

                if (empDetails != null) {
                    return new ApiResponse<>(true, List.of(empDetails), "Get employee details successful.");
                } else if (teacherDetails != null) {
                    return new ApiResponse<>(true, List.of(teacherDetails), "Get teacher details successful.");
                } else {
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }
            }
            return new ApiResponse<>(false, null, "Invalid token");
        } catch (UsernameNotFoundException e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateInfo(@RequestHeader(value = "Authorization") String bearerToken,
                                    @PathVariable Integer id,
                                    @RequestBody UserRequest data){
        String accessToken = bearerToken.replace("Bearer ", "");
        boolean isValidToken = jwtUtils.validateJwtToken(accessToken);

        if(isValidToken){
            String username = jwtUtils.getUserNameFromJwtToken(accessToken);
            Employee empDetails = employeeRepository.findByCode(username).orElse(null);
            Teacher teacherDetails = teacherRepository.findByTeacherCode(username).orElse(null);

            if (empDetails != null) {
                Employee update = employeeService.update(id, data);
                return new ApiResponse<>(true, List.of(update), "Update employee details successful.");
            } else if (teacherDetails != null) {
                Teacher update = teacherService.update(id, data);
                return new ApiResponse<>(true, List.of(update), "Get teacher details successful.");
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }
        return null;
    }

    @PutMapping("/changePassword/{id}")
    public ApiResponse<?> changePassword(@RequestHeader(value = "Authorization") String bearerToken,
                                        @PathVariable Integer id,
                                        @RequestBody ChangePasswordRequest data){
        try {
            String accessToken = bearerToken.replace("Bearer ", "");
            boolean isValidToken = jwtUtils.validateJwtToken(accessToken);
            if (isValidToken) {
                String username = jwtUtils.getUserNameFromJwtToken(accessToken);
                Employee empDetails = employeeRepository.findByCode(username).orElse(null);
                Teacher teacherDetails = teacherRepository.findByTeacherCode(username).orElse(null);

                if(!data.getNewPassword().equals(data.getConfirmPassword()))
                    return new ApiResponse<>(false, null, "Two password are not matches.");

                if (empDetails != null) {
                    boolean checkPassword = passwordEncoder.matches(data.getOldPassword(), empDetails.getPassword());
                    if (checkPassword){
                        String hashPassword = passwordEncoder.encode(data.getNewPassword());
                        employeeService.changePassword(id, hashPassword);
                        return new ApiResponse<>(true, null, "Password changed successful.");
                    }
                    return new ApiResponse<>(false, null, "Old password is incorrect.");
                } else if (teacherDetails != null) {
                    boolean checkPassword = passwordEncoder.matches(data.getOldPassword(), teacherDetails.getPassword());
                    if (checkPassword){
                        String hashPassword = passwordEncoder.encode(data.getNewPassword());
                        teacherService.changePassword(id, hashPassword);
                        return new ApiResponse<>(true, null, "Password changed successful.");
                    }
                    return new ApiResponse<>(false, null, "Old password is incorrect.");
                } else {
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }
            }
            return new ApiResponse<>(false, null, "Invalid token");
        } catch (UsernameNotFoundException e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
