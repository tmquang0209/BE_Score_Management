package com.tmquang.score.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subject_code", unique = true)
    private String subjectCode;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "final_exam_rate")
    private Float rate;

    public Subject() {
    }

    public Subject(String subjectCode, String subjectName, Integer credit) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }
}
