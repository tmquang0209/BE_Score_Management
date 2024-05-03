package com.tmquang.score.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

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

    @Column(name = "midterm__score")
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
}
