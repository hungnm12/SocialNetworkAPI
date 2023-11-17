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

import com.example.Social.Network.API.Model.ResDto.account_dto.CheckVerifyCodeResDto;
import com.example.Social.Network.API.Model.ResDto.account_dto.LogInResDto;
import com.example.Social.Network.API.Model.ResDto.account_dto.SignUpResDto;
import com.example.Social.Network.API.Model.ResDto.account_dto.UserResDto;
import com.example.Social.Network.API.Repository.SignUpRepo;
import com.example.Social.Network.API.Repository.TokenRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.AccountService;
import com.example.Social.Network.API.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final SignUpRepo signUpRepo;

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private TokenRepo tokenRepo ;
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final S3Service s3Service;
    public AccountServiceImpl(SignUpRepo signUpRepo, UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, S3Service s3Service) {
        this.signUpRepo = signUpRepo;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
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

        User user =  User.builder()
                .email(signUpReqDto.getEmail())
                .password(passwordEncoder.encode(signUpReqDto.getPassword()))
                .created(new Date(System.currentTimeMillis()))
                .build();
        var token  = jwtService.generateVerifyToken(user);
        signUpRepo.save(user);
        saveUserToken(user, token);

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
        userRepo.save(account.get());


        tokenRepo.deleteTokenByToken(verifyToken);
       return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,new CheckVerifyCodeResDto(account.get().getId(),account.get().isActive()));

    }


    public GeneralResponse getVerifyCode(String email) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        if( !isValidEmail(email ))
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The email is not valid");

        }
        var account = userRepo.findByEmail(email);
        if(account.isEmpty())
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"User is not exists");

        }
        if(account.get().isActive())
        {
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE,ResponseMessage.ACTION_BEEN_DONE_PRE,"User has been active");

        }
        var verifyCode  = jwtService.generateVerifyToken(account.get());
         Date timeCreateTokenAt =  JwtUtils.getCreateAt( jwtService,userRepo,verifyCode);
        if(new Date(System.currentTimeMillis()).getTime() -  timeCreateTokenAt.getTime() < 120000)
        {
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE,ResponseMessage.ACTION_BEEN_DONE_PRE,"Can not execute this operation");

        }
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,new SignUpResDto(email,verifyCode));
    }

    @Override
    public GeneralResponse login(SignInReqDto signInReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        if( !isValidEmail( signInReqDto.getEmail() )  )
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The email is not valid");

        }
        if(!isValidPassword(signInReqDto.getPassword())){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The password is not valid");
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

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInReqDto.getEmail(),
                            signInReqDto.getPassword()
                    )
            );
        } catch (Exception e) {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The user name or password is not valid");

        }

        var token = jwtService.generateToken(account.get());
        saveUserToken(account.get(),token);
        account.get().setCoins(10);
        account.get().setUserNameAccount(signInReqDto.getEmail().split("@")[1]);
        System.out.println(signInReqDto.getEmail().split("@")[0]);
        userRepo.save(account.get());
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE, LogInResDto.builder().id(account.get().getId()).avatar("https://imagev3.vietnamplus.vn/w660/Uploaded/2023/bokttj/2023_01_09/avatar_the_way_of_water.jpg.webp").username(account.get().getUserNameAccount()).token(token).active(account.get().isActive()).coins(account.get().getCoins()).build());
    }

    @Override
    public GeneralResponse logout(String token) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        if(token.isEmpty())
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID,ResponseMessage.TOKEN_INVALID,"Token is invalid");
        }
       var user =  JwtUtils.getUserFromToken(jwtService ,userRepo ,token);

        tokenRepo.deleteTokenByUserId(user.getId());

        return  new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,"Logout success");
    }

    @Override
    public GeneralResponse changeInfoAfterSignup(String token, String username, MultipartFile avatar) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        var userInToken = JwtUtils.getUserFromToken(jwtService,userRepo,token);

        var user = userRepo.findByEmail(userInToken.getEmail());
        if(token.isEmpty()|| user.isEmpty())
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID, "The Token is invalid ");
        }
        if(!isValidUsername(username,user.get().getEmail()))
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The username is not valid");
        }

//        if(avatar.isOverSize)
//        {
//
//        }
        Map<String,String> file =  s3Service.uploadFile(avatar);
        user.get().setAvatar(file.get("url"));
        user.get().setUserNameAccount(username);
        userRepo.save(user.get());
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE, UserResDto.builder().id(user.get().getId()).email(user.get().getEmail()).avatar(file.get("url")).created(user.get().getCreated()).username(user.get().getUserNameAccount()).build());
    }


    //    ---------------------------------------------
    boolean isValidEmail(String email){
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
