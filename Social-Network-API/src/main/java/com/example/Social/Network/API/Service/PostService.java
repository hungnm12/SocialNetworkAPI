package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface PostService {

    GeneralResponse addPost(String token, MultipartFile image, MultipartFile video, String described, String status)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException, IOException;



    GeneralResponse getPost(String token, Long Id)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse editPost(String token, Long Id, String described, String status, File image, String image_del, String image_sort, File video, String auto_accept)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse deletePost(String token, Long Id) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse reportPost(String token, Long Id, String subject, String details)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse feel(String token, Long Id, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse editPost(String token, Long Id, String described);

    GeneralResponse getMarkComment(Long Id, String index, String count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse setMarkComment(String token, Long id, String content, String index, String count, String markId, String type ) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

}
