package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.DTO.User;
import com.example.Social.Network.API.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignInService {
    private static UserRepository userRepository;


    public SignInService(UserRepository userRepository) {
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
        if (password.length()<6) {
            return false;
        }

        return true;
    }
    public static boolean emailExists(String email) {
        Optional<User> userOptional = userRepository.findByEmailAddress(email);
        return userOptional.isPresent();
    }


}

