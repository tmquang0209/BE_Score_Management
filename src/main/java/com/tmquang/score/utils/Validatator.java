package com.tmquang.score.utils;

public class Validatator {
    public static boolean isEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isPhone(String phone) {
        return phone.matches("^(\\+84|0)\\d{9,10}$");
    }

    public static boolean isPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }
}
