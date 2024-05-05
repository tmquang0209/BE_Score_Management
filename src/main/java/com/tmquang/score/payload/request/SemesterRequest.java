package com.tmquang.score.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SemesterRequest {
    @NotBlank
    private String semester;

    @NotBlank
    private Integer yearId;
}
