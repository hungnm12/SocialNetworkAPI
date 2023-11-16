package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.SignUpReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
public class demo {
    @PostMapping("/hello")
    public GeneralResponse test() {


        return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE,"hi");

    }
}
