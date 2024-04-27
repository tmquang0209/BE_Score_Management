package com.tmquang.score.models;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @JoinColumn(name = "department_code", referencedColumnName = "code")
    private Department department;

    public Employee() {
    }

    public Employee(String code, String name, String phone, String email, String password, Date dob, String gender,
            String role, Department department) {
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
        this.role = role;
        this.department = department;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
