package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SignInReqDto;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/login")
    public GeneralResponse signUp(@RequestBody SignInReqDto signInDto) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return accountService.login(signInDto);
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
    @PostMapping("/logout")
    public GeneralResponse logout(@RequestParam String token) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {

            return accountService.logout(token);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/getVerifyCode")
    public GeneralResponse getVerifyCode(@RequestParam String email) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {

            return accountService.getVerifyCode(email);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/changeInfoAfterSignup")
    public GeneralResponse changeInfoAfterSignup(@RequestParam String token, @RequestParam String username, @RequestParam MultipartFile avatar) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {

            return accountService.changeInfoAfterSignup(token,username,avatar);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/changePassword")
    public GeneralResponse changePassword(@RequestParam String token, @RequestParam String password, @RequestParam String newPassword) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {

            return accountService.changePassword(token,password,newPassword);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
}
