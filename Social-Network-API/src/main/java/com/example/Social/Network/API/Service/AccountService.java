package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SignInReqDto;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface AccountService {
    GeneralResponse signUp( SignUpReqDto signUpReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
    GeneralResponse checkVerifyCode(String email, String token)  throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse getVerifyCode(String email) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse login(SignInReqDto signInReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse logout(String token) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
    GeneralResponse changeInfoAfterSignup(String token,String username, MultipartFile avatar) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;


    GeneralResponse changePassword(String token, String password, String newPassword)throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse setUserInfo(String token, String username, String description, MultipartFile avatar, String address, String city, String country, MultipartFile coverImage, String link)  throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException,ResponseException;
    GeneralResponse getUserInfo(String token,Long userId)  throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException,ResponseException;
    GeneralResponse checkNewVersion(String token,String last_updated)  throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException,ResponseException;

}
