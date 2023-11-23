package com.example.Social.Network.API.utils;

import com.example.Social.Network.API.Model.Entity.PushSetting;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class CheckUtils {
    public static List<String> difference(PushSetting s1, PushSetting s2) throws IllegalAccessException {
        System.out.println(s1);
        System.out.println(s2);

        List<String> changedProperties = new ArrayList<>();
        for (Field field : s1.getClass().getDeclaredFields()) {
            // You might want to set modifier to public first (if it is not public yet)
            field.setAccessible(true);
            Object value1 = field.get(s1);
            Object value2 = field.get(s2);
            if (value1 != null && value2 != null) {
                System.out.println(field.getName() + "=" + value1);
                System.out.println(field.getName() + "=" + value2);
                if (!Objects.equals(value1, value2)) {
                    changedProperties.add(field.getName());
                }
            }
        }
        return changedProperties;
    }
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
    public static boolean isValidUsernameNoEmail(String username) {
        final String USERNAME_PATTERN = "^[a-zA-Z0-9]+$";
        // Kiểm tra username không được để trống
        if (username.isEmpty()) {
            return false;
        }

        // Kiểm tra username không chứa ký tự đặc biệt
        if (!Pattern.matches(USERNAME_PATTERN, username)) {
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

    public static String extractDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            if (domain != null) {
                // Remove "www." prefix if present
                if (domain.startsWith("www.")) {
                    domain = domain.substring(4);
                }
                return domain;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }
}
