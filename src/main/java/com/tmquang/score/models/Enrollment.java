package com.tmquang.score.models;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Data
@Getter
@Setter
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    public Enrollment() {
    }

    public Enrollment(Student student, Schedule schedule) {
        this.student = student;
        this.schedule = schedule;
    }
}
