package com.tmquang.score.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Table(name = "semesters")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year year;

    @Column(name = "semester", unique = true)
    private String semester;

    public Semester() {
    }

    public Semester(Year year, String semester) {
        this.year = year;
        this.semester = semester;
    }

}
