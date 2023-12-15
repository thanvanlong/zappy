package com.longtv.zappy.utils;

public class AuthUtils {
    public static boolean validatePhone(String phone){
        // Định dạng chuỗi regex cho số điện thoại với số 0 đằng trước và ít nhất 10 số
        String patternPhone = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

        return phone.matches(patternPhone);
    }
    public static boolean validatePassword(String password){
        // Định dạng chuỗi regex cho mật khẩu phải có ít nhất 8 kí tự
        String patternPassword = ".{6,}";

        return password.matches(patternPassword);
    }
}
