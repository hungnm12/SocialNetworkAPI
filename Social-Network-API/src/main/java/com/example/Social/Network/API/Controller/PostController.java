package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.Impl.PostServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @PostMapping("/add_post")
    public GeneralResponse addPost (
            @RequestParam String token,
            @RequestParam(required = false) File image,
            @RequestParam(required = false) File video,
            @RequestParam String described,
            @RequestParam String status)
            throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        try {
            return postService.addPost( token,image,video,described,status);
        } catch (ResponseException e) {
        return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
    }

}
    }
