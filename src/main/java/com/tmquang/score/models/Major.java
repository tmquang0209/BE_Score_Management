package com.tmquang.score.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "majors")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "major_code")
    private String majorCode;

    @Column(name = "major_name")
    private String majorName;

    public Major() {
    }

    public Major(String majorCode, String majorName) {
        this.majorCode = majorCode;
        this.majorName = majorName;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public String getMajorName() {
        return majorName;
    }
}
