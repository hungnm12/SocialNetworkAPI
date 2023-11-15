package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.SignUpRepo;
import com.example.Social.Network.API.Service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class SignUpServiceImpl implements SignUpService {

    @Autowired
//    @Qualifier("signupRepo")
    private final SignUpRepo signUpRepo;
//
    @Autowired
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public SignUpServiceImpl(SignUpRepo signUpRepo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.signUpRepo = signUpRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public GeneralResponse signUp(SignUpReqDto signUpReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {


        if (signUpRepo.existsByEmail(signUpReqDto.getEmail())){
            return new GeneralResponse(ResponseCode.USER_EXISTED, ResponseMessage.USER_EXISTED, "There is an account with that email address"+ signUpReqDto.getEmail());
        }

        else if (signUpReqDto.getEmail().isEmpty() && signUpReqDto.getPassword().isEmpty()){
            return new GeneralResponse(null,"Your email and password are not filled in yet" );
        }
        else if (!isValidEmail(signUpReqDto)){
            return new GeneralResponse(null,"dada",signUpReqDto);
        }
//    .3

        User user =  User.builder()
                .email(signUpReqDto.getEmail())
                .password(passwordEncoder.encode(signUpReqDto.getPassword()))
                .created(LocalDateTime.now())
                .active(true)
                .build();
        var token  = jwtService.generateToken(user);
        signUpRepo.save(user);
//        signUpReqDto.setUuid(token);
        System.out.println(ResponseCode.OK_CODE);
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

//    boolean isValidPassword(SignUpReqDto signUpReqDto) {
////        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
//        if (signUpReqDto.getPassword() == null || signUpReqDto.getPassword().isEmpty()){
//            return false;
//        }
//        return signUpReqDto.getPassword().matches(EMAIL_REGEX);
//    }






}
