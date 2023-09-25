package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.DTO.User;
import com.example.Social.Network.API.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {
    private static UserRepository userRepository;


    public SignupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    public static boolean isValidEmail(String email) {
        if (!email.contains("@")) {
            return false;
        }
        String[] parts = email.split("@");
        if (parts[1].isEmpty()) {
            return false;
        }
        return true;
    }
    public static boolean isValidPassword(String password) {
        if (password.length()<6 || password.length()>10) {
            return false;
        }

        return true;
    }
    public static boolean isEmailIdenticalPassword(String password, String email) {
        if (password.equals(email)) {
            return false;
        }
        return true;
    }
    public static boolean emailExists(String email) {
        Optional<User> userOptional = userRepository.findByEmailAddress(email);
        return userOptional.isPresent();
    }

    public static boolean isBlank(String email, String password) {
        if (password.isEmpty() || email.isEmpty() || email.isEmpty() && password.isEmpty()){
            return false;
        }
        return true;
    }


}

