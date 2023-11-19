package com.example.Social.Network.API.utils;

import java.util.regex.Pattern;

public class CheckUtils {
    public static boolean isValidPassword(String password) {
        // Allowed characters are letters, numbers, underscore, length between 6 and 30 characters
        String regChar = "^[\\w_]{6,30}$";
        // Phone number pattern
        if(password.length() < 8 )
        {
            return false;
        }
        // Check if password matches the character pattern
        return Pattern.matches(regChar, password);
    }
    public static boolean isValidEmail(String email){
        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        if (email== null || email.isEmpty()){
            return false;
        }
        return email.matches(EMAIL_REGEX);

    }
    public static boolean isValidUsername(String username, String email) {
        final String USERNAME_PATTERN = "^[a-zA-Z0-9]+$";
        // Kiểm tra username không được để trống
        if (username.isEmpty()) {
            return false;
        }

        // Kiểm tra username không chứa ký tự đặc biệt
        if (!Pattern.matches(USERNAME_PATTERN, username)) {
            return false;
        }

        // Kiểm tra username không trùng với email
        if (username.equalsIgnoreCase(email)) {
            return false;
        }

        // Kiểm tra username không quá ngắn hoặc quá dài
        int minLength = 3; // Độ dài tối thiểu cho username
        int maxLength = 20; // Độ dài tối đa cho username
        if (username.length() < minLength || username.length() > maxLength) {
            return false;
        }

        // Kiểm tra username không là đường dẫn, email hoặc địa chỉ
        if (username.contains("/") || username.contains("@") || username.contains(".")) {
            return false;
        }

        return true;
    }
}
