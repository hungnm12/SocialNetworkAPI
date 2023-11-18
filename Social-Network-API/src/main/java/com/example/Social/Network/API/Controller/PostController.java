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
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam("token") String token,
            @RequestParam(required = false)  MultipartFile image,
            @RequestParam(required = false) MultipartFile video,
            @RequestParam("described") String described,
            @RequestParam("status") String status)
            throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        try {
            return postService.addPost( token,image,video,described,status);
        } catch (ResponseException e) {
        return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
    }


}

    @PostMapping("/edit_post")
   public GeneralResponse editPost  (
           @RequestParam("token") String token,
            @RequestParam("id") Long Id,
           @RequestParam("status") String status,
           @RequestParam(required = false) MultipartFile image,
           @RequestParam(required = false) MultipartFile video,
           @RequestParam("image_del") String image_del,
           @RequestParam("image_sort") String image_sort,
           @RequestParam("described") String described,
           @RequestParam("auto_accept") String auto_accept
            ) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        try {
            return postService.editPost(token, Id,described,status,image,image_del,image_sort,video,auto_accept);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }
    }

    }
