package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.Impl.SignUpServiceImpl;
import com.example.Social.Network.API.Service.SignUpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
//import java.net.http.HttpRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class SignUpController {

    @Autowired
    private SignUpServiceImpl signUpService;

    @PostMapping("/signup")

    public GeneralResponse signUp(@RequestBody SignUpReqDto signUpReqDto) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return signUpService.signUp( signUpReqDto);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }

}
