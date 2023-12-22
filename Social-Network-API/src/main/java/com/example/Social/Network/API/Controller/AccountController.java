package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SignInReqDto;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.tools.javac.Main;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
//import java.net.http.HttpRequest;
import java.io.File;
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
    @PostMapping("/set_user_info")
    public GeneralResponse setUserInfo(@RequestParam String token, @RequestParam(required = false) String username, @RequestParam(required = false) String description,@RequestParam(required = false) MultipartFile avatar , @RequestParam(required = false) String address, @RequestParam(required = false) String city, @RequestParam(required = false) String country , @RequestParam(required = false) MultipartFile coverImage,@RequestParam(required = false) String link) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {

            return accountService.setUserInfo(token,username,description,avatar,address, city,country,coverImage,link);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/get_user_info")
    public GeneralResponse getUserInfo(@RequestParam String token,@RequestParam(required = false) Long userId) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {

            return accountService.getUserInfo(token,userId);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/check_new_version")
    public GeneralResponse checkNewVersion(@RequestParam("token") String token,@RequestParam("last_update") String last_update) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {

            return accountService.checkNewVersion(token,last_update);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
}
