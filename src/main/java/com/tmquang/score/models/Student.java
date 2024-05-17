package com.tmquang.score.models;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "student_code", unique = true)
    private String studentCode;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "dob")
    private Date dob;

    @NotBlank
    @Column(name = "gender", nullable = false)
    private String gender;

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Major major;

    public Student() {
    }

    public Student(String studentCode, String name, String email, String phone, String address, Date dob,
            String gender, Major major) {
        this.studentCode = studentCode;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.major = major;
    }
}
