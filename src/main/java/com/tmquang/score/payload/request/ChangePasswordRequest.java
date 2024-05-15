package com.tmquang.score.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
