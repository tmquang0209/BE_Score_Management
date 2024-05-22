package com.tmquang.score.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreRequest {
    @NotBlank
    String studentCode;
    Float midtermTest;
    Float finalTest;
}
