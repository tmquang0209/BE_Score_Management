package com.tmquang.score.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {
    Integer semesterId;
    Integer subjectId;
    String className;
}
