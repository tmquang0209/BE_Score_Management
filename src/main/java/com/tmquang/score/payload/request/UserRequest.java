package com.tmquang.score.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserRequest {
    private String email;
    private String phone;
    private String address;
    private Date dob;
}
