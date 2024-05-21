package com.tmquang.score.payload.response;

import com.tmquang.score.models.Semester;
import com.tmquang.score.models.Year;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YearAndSemesterResponse {
    private Year year;
    private List<Semester> semesters;

    public YearAndSemesterResponse(Year year, List<Semester> semesters) {
        this.year = year;
        this.semesters = semesters;
    }
}
