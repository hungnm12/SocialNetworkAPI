package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.Image;
import com.example.Social.Network.API.Model.Entity.Post;
import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Model.Entity.Video;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.ImageRepo;
import com.example.Social.Network.API.Repository.PostRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static com.example.Social.Network.API.utils.JwtUtils.extractUserDetailsFromToken;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
@Autowired
private JwtService jwtService;

@Autowired
private PostRepo postRepo;

@Autowired
private ImageRepo imageRepo;

@Autowired
private S3Service s3Service;
@Autowired
private UserRepo userRepo;
    @Override
    public GeneralResponse addPost(String token, MultipartFile image, MultipartFile video, String described, String status)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
      try {
          image.getOriginalFilename();
          String urlImg = s3Service.uploadFile(image);
          Post post1 = new Post();
          Image image1 = new Image();
          image1.setUrlImage(urlImg);
          image1.setPost(post1);
          Video video1 = new Video(urlImg,post1);

          post1.setDescribed(described);
          post1.setStatus(status);
          post1.setUrl(generatePostUrl());
          User user = getUserFromToken(token);
          post1.setUser(user);
          chargeOnPost(user, post1);
          postRepo.save(post1);
          var t = getUserFromToken(token);

          if (user.getCoins() < 1){
              return new GeneralResponse(null,"Not enough coins","");
          }

          return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, "");
      }
      catch (RuntimeException e ) {
          return new GeneralResponse(ResponseCode.EXCEPTION_ERROR,"","");

      }
    }



    private User getUserFromToken(String token) {
       String  username = jwtService.extractUsername(token);

        return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));

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
    public GeneralResponse editPost(String token, Long Id, String described, String status, MultipartFile image, String image_del, String image_sort, MultipartFile video, String auto_accept) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        User user = getUserFromToken(token);

        Post existPost = postRepo.findAllById(Id);

        existPost.setUser(user);

        chargeOnPost(user,existPost);

        if (user.getCoins() < 1){
            return new GeneralResponse(null,"Not enough coins","");
        }

        if (!image_del.isEmpty()) {
            Image imageToDelete = imageRepo.findById(Long.parseLong(image_del)).get();
            existPost.getImages().remove(imageToDelete);
            imageRepo.delete(imageToDelete);
        }

        if (image != null && !image.isEmpty()) {
            String urlImg = s3Service.uploadFile(image);
            Image image1 = new Image();
            image1.setUrlImage(urlImg);
            image1.setPost(existPost);
            existPost.getImages().add(image1);
        }

        if (!image_sort.isEmpty()) {
            List<Long> imageIds = Arrays.stream(image_sort.split(",")).map(Long::parseLong).collect(Collectors.toList());
            ArrayList<Image> sortedImage = (ArrayList<Image>) existPost.getImages().stream()
                    .filter(image1 -> imageIds.contains(image1.getId()))
                    .sorted(Comparator.comparingLong(Image::getId))
                    .collect(Collectors.toList());
            existPost.setImages(sortedImage);
        }

//        if (video != null && !video.isEmpty()) {
//            String urlVideo = s3Service.uploadFile(video);
//            Video video1 = new Video(urlVideo, existPost);
//            existPost.setVideos(video1);
//        }

        existPost.setStatus(status);
        existPost.setDescribed(described);

        postRepo.save(existPost);



        return new GeneralResponse(ResponseCode.OK_CODE, "Successfully Edited", "");





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
    public GeneralResponse getMarkComment(Long Id, String index, String count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse setMarkComment(String token, Long id, String content, String index, String count, String markId, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }




}
