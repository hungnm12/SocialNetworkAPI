package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;

import java.net.http.HttpRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface SignUpService {
    GeneralResponse signUp(HttpRequest request, SignUpReqDto signUpReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

}
