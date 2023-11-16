package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SignInReqDto;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface AccountService {
    GeneralResponse signUp( SignUpReqDto signUpReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
    GeneralResponse checkVerifyCode(String email, String token)  throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse getVerifyCode(String email) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse login(SignInReqDto signInReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse logout(String token) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
}
