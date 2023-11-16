package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
//import java.net.http.HttpRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/signup")
    public GeneralResponse signUp(@RequestBody SignUpReqDto signUpReqDto) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return accountService.signUp( signUpReqDto);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/checkVerifyCode")
    public GeneralResponse checkVerifyCode(@RequestParam String email,@RequestParam String token) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {

            return accountService.checkVerifyCode(email,token);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }

}
