package com.tmquang.score.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "semester_id", referencedColumnName = "id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @Column(name = "midterm_score")
    private float midtermScore;

    @Column(name = "final_score")
    private float finalScore;

    @Column(name = "score")
    private float score;

    public Score() {
    }

    public Score(Semester semester, Student student, float finalScore, float midtermScore, float score) {
        this.semester = semester;
        this.student = student;
        this.finalScore = finalScore;
        this.midtermScore = midtermScore;
        this.score = score;
    }

    public Score(Semester semester, Subject subject, Student student, float midtermScore, float finalScore) {
        this.finalScore = finalScore;
        this.semester = semester;
        this.student = student;
        this.subject = subject;
        this.midtermScore = midtermScore;
    }

    public Score(Semester semester, Subject subject, Student student, float midtermScore, float finalScore, float score) {
        this.finalScore = finalScore;
        this.semester = semester;
        this.student = student;
        this.subject = subject;
        this.midtermScore = midtermScore;
        this.score = score;
    }
}
