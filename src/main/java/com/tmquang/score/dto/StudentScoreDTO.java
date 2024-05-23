package com.tmquang.score.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentScoreDTO {
    private String studentCode;
    private String studentName;
    private Float midtermScore;
    private Float finalScore;
    private Float score;

    public StudentScoreDTO(String studentCode, String studentName, Float midtermScore, Float finalScore, Float score) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
        this.score = score;
    }
}
