package com.tmquang.score.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {
    private String subjectCode;
    private String subjectName;
    private Integer credit;
    private Float rate;
}
