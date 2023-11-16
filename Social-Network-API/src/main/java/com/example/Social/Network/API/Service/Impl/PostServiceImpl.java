package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.Post;
import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Model.ResDto.AddPostResDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.PostRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.example.Social.Network.API.utils.JwtUtils.extractUserDetailsFromToken;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

@Autowired
private PostRepo postRepo;

@Autowired
private UserRepo userRepo;
    @Override
    public GeneralResponse addPost(String token, MultipartFile image, MultipartFile video, String described, String status)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException, IOException {

        log.info("[addPost]- start with input: {}", token, described, image, video,status);


            byte[] imageBytes = image.getBytes();


        Post post1 = new Post();
        post1.setDescribed(described);
        post1.setStatus(status);
        post1.setImage();
        post1.setVideo();
        post1.setUrl(generatePostUrl());

        User user = getUserFromToken(token);
        post1.setUser(user);
        chargeOnPost(user, post1);
        postRepo.save(post1);

        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, (post1.getId() + post1.getUrl() + user.getCoins()));
    }

    private User getUserFromToken(String token) {
        // Extract the user information from the token
        UserDetails userDetails = extractUserDetailsFromToken(token);

        // Get the user's email
        String email = userDetails.getUsername();

        // Find the user by email
        User user = userRepo.findByEmail(email).get();

        return user;
    }

    private String generatePostUrl() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert UUID to a string
        String uuidString = uuid.toString();

        // Create a URL using the UUID string
        String url =  uuidString;

        return url;
    }


    private void chargeOnPost(User user, Post post) {

        int currentCoin = user.getCoins();

        currentCoin -= 1;

        user.setCoins(currentCoin);

        userRepo.save(user);

    }

    @Override
    public GeneralResponse getPost(String token, Long Id) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return new GeneralResponse();
    }

    @Override
    public GeneralResponse editPost(String token, Long Id, String described, String status, File image, String image_del, String image_sort, File video, String auto_accept) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse deletePost(String token, Long Id) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse reportPost(String token, Long Id, String subject, String details) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse feel(String token, Long Id, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse editPost(String token, Long Id, String described) {
        return null;
    }

    @Override
    public GeneralResponse getMarkComment(Long Id, String index, String count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse setMarkComment(String token, Long id, String content, String index, String count, String markId, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }




}
