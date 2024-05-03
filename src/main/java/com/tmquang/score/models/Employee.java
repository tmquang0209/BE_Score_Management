package com.tmquang.score.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.tmquang.score.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private String gender;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "employee_roles",
            joinColumns = @JoinColumn(name = "emp_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    public Employee(String code, String name, String phone, String email, String password, Date dob, String gender, Set<Role> roles) {
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
        this.roles = roles;
    }
}
