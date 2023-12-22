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

import com.example.Social.Network.API.Model.ResDto.account_dto.*;
import com.example.Social.Network.API.Model.ResDto.account_dto.check_new_item_res.CheckNewItemRes;
import com.example.Social.Network.API.Model.ResDto.account_dto.check_new_item_res.UserResItem;
import com.example.Social.Network.API.Model.ResDto.account_dto.check_new_item_res.Version;
import com.example.Social.Network.API.Repository.FriendListRepo;
import com.example.Social.Network.API.Repository.SignUpRepo;
import com.example.Social.Network.API.Repository.TokenRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.AccountService;
import com.example.Social.Network.API.utils.CheckUtils;
import com.example.Social.Network.API.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    ModelMapper modelMapper = new ModelMapper();

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

    @Autowired
    private final FriendListRepo friendListRepo;
    private final S3Service s3Service;
    public AccountServiceImpl(SignUpRepo signUpRepo, UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, FriendListRepo friendListRepo, S3Service s3Service) {
        this.signUpRepo = signUpRepo;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.friendListRepo = friendListRepo;
        this.s3Service = s3Service;
    }

    @Override
    public GeneralResponse  signUp(SignUpReqDto signUpReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        if(!CheckUtils.isValidEmail(signUpReqDto.getEmail())){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The email is not valid");
        }

        if (signUpRepo.existsByEmail(signUpReqDto.getEmail())){
            return new GeneralResponse(ResponseCode.USER_EXISTED, ResponseMessage.USER_EXISTED, "There is an account with that email address :"+ signUpReqDto.getEmail());
        }

        else if (signUpReqDto.getEmail().isEmpty() && signUpReqDto.getPassword().isEmpty()){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"Your email and password are not filled in yet" );
        }
        else if (!CheckUtils.isValidPassword(signUpReqDto.getPassword())){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The password are not valid" );

        }

        User user =  User.builder()
                .email(signUpReqDto.getEmail())
                .password(passwordEncoder.encode(signUpReqDto.getPassword()))
                .created(new Date(System.currentTimeMillis()))
                .avatar("https://imagev3.vietnamplus.vn/w660/Uploaded/2023/bokttj/2023_01_09/avatar_the_way_of_water.jpg.webp")
                .build();

        user.setUserNameAccount(signUpReqDto.getEmail().split("@")[0]);

        var token  = jwtService.generateVerifyToken(user);
        signUpRepo.save(user);
        saveUserToken(user, token);
        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,new SignUpResDto(user.getEmail(),token ) );

    }

    @Override
    public GeneralResponse checkVerifyCode(String email, String verifyToken) throws ResponseException {

        if(!CheckUtils.isValidEmail(email)){
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
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The verifyCode is not valid");

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
        if( !CheckUtils.isValidEmail(email ))
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
         Date timeCreateTokenAt =  JwtUtils.getCreateAt( jwtService,verifyCode);
        if(new Date(System.currentTimeMillis()).getTime() -  timeCreateTokenAt.getTime() < 120000)
        {
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE,ResponseMessage.ACTION_BEEN_DONE_PRE,"Can not execute this operation");

        }
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,new SignUpResDto(email,verifyCode));
    }

    @Override
    public GeneralResponse login(SignInReqDto signInReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        if( !CheckUtils.isValidEmail( signInReqDto.getEmail() )  )
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The email is not valid");

        }
        if(!CheckUtils.isValidPassword(signInReqDto.getPassword())){
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
        userRepo.save(account.get());
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE, LogInResDto.builder().id(account.get().getId()).avatar(account.get().getAvatar()).username(account.get().getUserNameAccount()).token(token).active(account.get().isActive()).coins(account.get().getCoins()).build());
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
        if(!CheckUtils.isValidUsername(username,user.get().getEmail()))
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

    @Override
    public GeneralResponse changePassword(String token, String password, String newPassword) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        if(password.isEmpty()|| newPassword.isEmpty())
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The password is not valid");

        }
        if(!CheckUtils.isValidPassword(newPassword))
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The new Password is not valid");

        }
        int n = lcs(password, newPassword);
        if ((double) n / password.length() >= 0.8 || (double) n / newPassword.length() >= 0.8) {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID, "The Param is not valid");



        }
        var user =JwtUtils.getUserFromToken(jwtService,userRepo,token);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, "Change password success");
    }

    @Override
    public GeneralResponse setUserInfo(String token, String username, String description, MultipartFile avatar, String address, String city, String country, MultipartFile coverImage, String link) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        List<String> blockedAddress = new ArrayList<>();
        blockedAddress.add("North Korea");
        List<String> urlListBlock = new ArrayList<>();
        // Adding URLs to the list
        urlListBlock.add("https://vnhackers.com");

        if(token.isEmpty())
        {
            return new GeneralResponse(ResponseCode.PARAMETER_NOT_ENOUGH,ResponseMessage.PARAMETER_NOT_ENOUGH,"The parameter is not enough");

        }


            if(username!=null&&  !CheckUtils.isValidUsernameNoEmail(username))
            {
                return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The username is not valid");
            }



        if(description.length() > 150 )
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The description is not valid");
        }
        var user = JwtUtils.getUserFromToken(jwtService,userRepo,token);
        if(!userRepo.existsUserById(user.getId()))
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"The User is not valid");

        }
        if(!user.isActive())
        {
            return new GeneralResponse(ResponseCode.NOT_ACCESS,ResponseMessage.NOT_ACCESS,"The user is block by the system");
        }
        for (String a:blockedAddress
             ) {
            if(address.contains(a) || city.contains(a)||country.contains(a))
            {
                return new GeneralResponse(ResponseCode.NOT_ACCESS,ResponseMessage.NOT_ACCESS,"The country does not support , you need logout");

            }
        }
        for(String l : urlListBlock){
            var domainName =  CheckUtils.extractDomainName(l);
            if(link.equals(domainName))
            {
                return new GeneralResponse(ResponseCode.NOT_ACCESS,ResponseMessage.NOT_ACCESS,"The Link is blocked");

            }
        }

        var beforeInformation = userRepo.findById(user.getId());


            if( avatar!= null && !avatar.isEmpty())
            {
                if(!user.getAvatar().isEmpty())
                {
                    s3Service.deleteFile(user.getAvatar());
                }
                var urlAvatar = s3Service.uploadFile(avatar).get("url");
                user.setAvatar(urlAvatar);

            }
            else{
                user.setAvatar("");
            }


            if( coverImage!=null&& !coverImage.isEmpty())
            {
                if(!user.getCoverImage().isEmpty())
                {
                    s3Service.deleteFile(user.getCoverImage());
                }
                var urlCoverImage = s3Service.uploadFile(coverImage).get("url");
                user.setCoverImage(urlCoverImage);

            }
            else {
                user.setCoverImage("");
            }

         user.setUserNameAccount(username);
         user.setAddress(address.isEmpty() ? "" : address);
         user.setCity(city.isEmpty() ? "" : city );
         user.setCountry(country.isEmpty() ? "":country );
         user.setLink(link.isEmpty() ?"":link );
         user.setDescription(description.isEmpty() ? "": description ) ;
//         if(beforeInformation.isEmpty())
//         {
//
//         }
//         if(beforeInformation.get().equals(user))
//         {
//             return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE,"Action has done previously by this user");
//
//         }
         userRepo.save(user);
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,user);
    }

    @Override
    public GeneralResponse getUserInfo(String token,Long userId) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException, ResponseException {
        var user = JwtUtils.getUserFromToken(jwtService, userRepo, token);

        if (userId == null) {
            userId = user.getId();

        }
        var userInfo = userRepo.findById(userId);

        if (userInfo.isEmpty()) {

            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The User is not exists");

        }
        if(!userInfo.get().isActive()){
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "The User has been blocked");
        }

        int numberFriendOfUser= friendListRepo.findUserFriendByTheUserIdNotPageable(userId ).size();
        ModelMapper x = new ModelMapper();
        long isFriend = friendListRepo.isFriend(user.getId(),userId);
//        =  modelMapper.typeMap(User.class, GetUserInfoResDto.class)
//                .addMapping(User::getListing, GetUserInfoResDto::setListing);
       var res   =   x.map(userInfo, GetUserInfoResDto.class);
        System.out.println(res);
        res.set_friend(isFriend==1);
        res.setListing(String.valueOf(numberFriendOfUser));
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,res);
    }

    @Override
    public GeneralResponse checkNewVersion(String token, String last_updated) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException, ResponseException {

        if(token==null||token.isEmpty()||last_updated==null|| last_updated.isEmpty() )
        {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID,"");

        }

        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(user == null)
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if(!jwtService.isTokenValid(token,user))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }
        UserResItem userResItem = new UserResItem(user.getId().toString(),user.isEnabled() ? "1":"0");
        Version version= new Version("1.1.1","1","null");
        CheckNewItemRes checkNewItemRes= CheckNewItemRes.builder()
                .version(version)
                .user(userResItem)
                .badge("0")
                .unread_message("0")
                .now("1.1.1")
                .build();

        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,checkNewItemRes);
    }


    //    ---------------------------------------------



    public int lcs(String s1, String s2) {
        int[][] result = new int[s2.length() + 1][s1.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            result[0][i] = 0;
        }

        for (int i = 0; i < s2.length(); i++) {
            result[i][0] = 0;
            for (int j = 0; j < s1.length(); j++) {
                result[i + 1][j + 1] = s1.charAt(j) == s2.charAt(i) ? 1 + result[i][j] : 0;
            }
        }

        int maxLength = result[0][0];
        for (int i = 1; i <= s2.length(); i++) {
            for (int j = 1; j <= s1.length(); j++) {
                if (result[i][j] > maxLength) {
                    maxLength = result[i][j];
                }
            }
        }

        return maxLength;
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

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }


}
