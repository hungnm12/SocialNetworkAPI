package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.DTO.Response;
import com.example.Social.Network.API.DTO.SignUpDTO;
import com.example.Social.Network.API.DTO.User;
import com.example.Social.Network.API.Repository.SignUpDTORepository;
import com.example.Social.Network.API.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {
    private static UserRepository userRepository;


    public SignupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    static
    SignUpDTORepository signUpDTORepository;

    public Response SignUp(SignUpDTO signUpDTO) {

        if (emailExists(signUpDTO.getEmail())) {
            return new Response(ResponseCode.USER_EXISTED, ResponseMessage.USER_EXISTED, null);
        }

        if (isValidEmail(signUpDTO.getEmail())) {
            return new Response(null,"Email is invalid", null);
        }

        if (isValidPassword(signUpDTO.getPassword())) {
            return new Response(null, "Password is invalid", null);
        }

        if (isEmailIdenticalPassword(signUpDTO.getPassword(),signUpDTO.getEmail())) {
            return new Response(null,"Email is identical to Password",null);
        }

        if (isBlank(signUpDTO.getPassword(),signUpDTO.getEmail())) {
            return new Response(null,"Please fill all fields", null);
        }

        return new Response(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, null);
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
//        Optional<User> userOptional = userRepository.findByEmailAddress(email);
//        return userOptional.isPresent();

        Optional<SignUpDTO> signUpDTOOptional = signUpDTORepository.findByEmailAddress(email);
        return signUpDTOOptional.isPresent();
    }



    public static boolean isBlank(String email, String password) {
        if (password.isEmpty() || email.isEmpty() || email.isEmpty() && password.isEmpty()){
            return false;
        }
        return true;
    }


}

