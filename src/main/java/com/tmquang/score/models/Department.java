package com.tmquang.score.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_at")
    private String updateAt;

    public Department() {
    }

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
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

}
