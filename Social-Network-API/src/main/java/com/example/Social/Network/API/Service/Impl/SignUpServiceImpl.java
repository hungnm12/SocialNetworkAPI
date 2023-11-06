package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.UserEntity;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.SignUpRepo;
import com.example.Social.Network.API.Service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j

public class SignUpServiceImpl implements SignUpService {

    @Autowired
//    @Qualifier("signupRepo")
    private SignUpRepo signUpRepo;
//
//    @Autowired
//    private SignUpService signUpService;

    private PasswordEncoder passwordEncoder;

    @Override
    public GeneralResponse signUp(HttpRequest request, SignUpReqDto signUpReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {


        if (signUpRepo.existsByEmail(signUpReqDto.getEmail())){
            return new GeneralResponse(ResponseCode.USER_EXISTED, ResponseMessage.USER_EXISTED, "There is an account with that email address"+ signUpReqDto.getEmail());
        }

        else if (signUpReqDto.getEmail().isEmpty() && signUpReqDto.getPassword().isEmpty()){
            return new GeneralResponse(null,"Your email and password are not filled in yet" );
        }
        else if (isValidEmail(signUpReqDto)){
            return new GeneralResponse(null,"");
        }
        else if (isValidPassword(signUpReqDto)){
            return new GeneralResponse(null,"");
        }

        UserEntity user = new UserEntity();
        user.setEmail(signUpReqDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpReqDto.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setActive(true);
        signUpRepo.save(user);

        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, signUpReqDto);

    }




//    ---------------------------------------------
    boolean isValidEmail(SignUpReqDto signUpReqDto){
        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        if (signUpReqDto.getEmail() == null || signUpReqDto.getEmail().isEmpty()){
            return false;
        }
        return signUpReqDto.getEmail().matches(EMAIL_REGEX);

    }

    boolean isValidPassword(SignUpReqDto signUpReqDto) {
        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        if (signUpReqDto.getPassword() == null || signUpReqDto.getPassword().isEmpty()){
            return false;
        }
        return signUpReqDto.getPassword().matches(EMAIL_REGEX);
    }



    @Override
    public String createVerificationTokenForUser(UserEntity user) {
        return null;
    }


}
