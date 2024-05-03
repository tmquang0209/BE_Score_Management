package com.tmquang.score.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "day")
    private String day;

    @Column(name = "shift")
    private String shift;

    @Column(name = "room")
    private String room;

    @Column(name = "current_student")
    private Integer currentStudent;

    @Column(name = "max_student")
    private Integer maxStudent;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Schedule() {
    }

    public Schedule(Major major, Semester semester, Subject subject, String day, String shift, String room,
            Integer currentStudent, Integer maxStudent, Teacher teacher) {
        this.major = major;
        this.semester = semester;
        this.subject = subject;
        this.day = day;
        this.shift = shift;
        this.room = room;
        this.currentStudent = currentStudent;
        this.maxStudent = maxStudent;
        this.teacher = teacher;
    }

}
