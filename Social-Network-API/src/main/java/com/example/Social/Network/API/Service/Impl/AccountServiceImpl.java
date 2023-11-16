package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.Token;
import com.example.Social.Network.API.Model.Entity.TokenType;
import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Model.ReqDto.SignInReqDto;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Model.ResDto.account_dto.LogInResDto;
import com.example.Social.Network.API.Model.ResDto.account_dto.LoginResDto;
import com.example.Social.Network.API.Model.ResDto.account_dto.SignUpResDto;
import com.example.Social.Network.API.Repository.SignUpRepo;
import com.example.Social.Network.API.Repository.TokenRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
//    @Qualifier("signupRepo")
    private final SignUpRepo signUpRepo;

    @Autowired
    private final UserRepo userRepo;
//
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private TokenRepo tokenRepo ;
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(SignUpRepo signUpRepo, UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.signUpRepo = signUpRepo;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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
        else if (!isValidEmail(signUpReqDto.getEmail())){
            return new GeneralResponse(null,"dada",signUpReqDto);
        }
//    .3

        User user =  User.builder()
                .email(signUpReqDto.getEmail())
                .password(passwordEncoder.encode(signUpReqDto.getPassword()))
                .created(LocalDateTime.now())
//                .active(true)
                .build();
        var token  = jwtService.generateVerifyToken(user);
        signUpRepo.save(user);
        saveUserToken(user, token);
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        signUpReqDto.getEmail(),
//                        signUpReqDto.getPassword()
//
//                )
//        );
        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,new SignUpResDto(user.getEmail(),token ) );

    }

    @Override
    public GeneralResponse checkVerifyCode(String email, String verifyToken) throws ResponseException {

        if(!isValidEmail(email)){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The email is not valid");
        }
        var account = userRepo.findByEmail(email);

        if(account.isEmpty())
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"User is not exists");


        }
        var verifyCode = tokenRepo.findTokenByToken(verifyToken);
        if(verifyCode.isEmpty())
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The verifyCode is not exists");

        }
        if(account.get().isActive())
        {
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE,ResponseMessage.ACTION_BEEN_DONE_PRE,"The user has been active");

        }
        account.get().setActive(true);

//        var token = jwtService.generateToken(account.get());
//        revokeAllUserTokens(account.get());
//        saveUserToken(account.get(),token);
        tokenRepo.deleteTokenByToken(verifyToken);
       return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,new LogInResDto(account.get().getId(),account.get().isActive()));

    }


    public GeneralResponse getVerifyCode(String email) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse login(SignInReqDto signInReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        if( !isValidEmail( signInReqDto.getEmail() )  )
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The email is not valid");

        }
        var account = userRepo.findByEmail(signInReqDto.getEmail());
        if(account.isEmpty())
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"User is not exists");

        }
        if(!account.get().isActive())
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"User is not validated");

        }
        if(!isValidPassword(signInReqDto.getPassword())){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The password is not valid");
        }
        var token = jwtService.generateToken(account.get());
        saveUserToken(account.get(),token);
//        revokeAllUserTokens(account.get());
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE, LoginResDto.builder().id(account.get().getId()).avatar("https://imagev3.vietnamplus.vn/w660/Uploaded/2023/bokttj/2023_01_09/avatar_the_way_of_water.jpg.webp").email(account.get().getEmail()).token(token).coins(10).build());
    }

    @Override
    public GeneralResponse logout(String token) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

//        tokenRepo.deleteTokenByUser()
        return null;
    }


    //    ---------------------------------------------
    boolean isValidEmail(String email){
        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        if (email== null || email.isEmpty()){
            return false;
        }
        return email.matches(EMAIL_REGEX);

    }

    public static boolean isValidPassword(String password) {
        // Allowed characters are letters, numbers, underscore, length between 6 and 30 characters
        String regChar = "^[\\w_]{6,30}$";
        // Phone number pattern

        // Check if password matches the character pattern
        return Pattern.matches(regChar, password);
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;

        System.out.println(validUserTokens);
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }


}
