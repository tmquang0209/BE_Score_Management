package com.tmquang.score.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private List<T> data;
    private String message;

    public ApiResponse(boolean success, List<T> data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }
}
