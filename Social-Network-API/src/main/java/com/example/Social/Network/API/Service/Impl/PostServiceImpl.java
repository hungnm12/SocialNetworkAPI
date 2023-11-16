package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.Post;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.PostRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

@Autowired
private PostRepo postRepo;

@Autowired
private UserRepo userRepo;
    @Override
    public GeneralResponse addPost(String token, File image, File video, String described, String status)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException {



        Post post1 = new Post();
        post1.setDescribed(described);
        post1.setStatus(status);
        post1.setImage(image);
        post1.setVideo(video);
        post1.setUrl(generatePostUrl());

        postRepo.save(post1);

        return new GeneralResponse();
    }

    private String generatePostUrl() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert UUID to a string
        String uuidString = uuid.toString();

        // Create a URL using the UUID string
        String url = "https://example.com/posts/" + uuidString;

        return url;
    }



}
