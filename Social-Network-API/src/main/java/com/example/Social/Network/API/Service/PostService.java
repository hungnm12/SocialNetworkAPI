package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.GetListPostsReqDto;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.GetMarkCommentReqDto;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.GetPostReqDto;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.SetMarkCommentReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.DelSavedSearchReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.GetSavedSearchReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.SearchFunctionReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Model.ResDto.PostResDto.GetListPostsResDto;
import com.example.Social.Network.API.Model.ResDto.PostResDto.GetPostResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface PostService {

    GeneralResponse addPost(String token, MultipartFile image, MultipartFile video, String described, String status)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException, IOException;



GeneralResponse getPost(String token, Long Id) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse editPost(String token, Long Id, String described, String status, MultipartFile image, String image_del, String image_sort, MultipartFile video, String auto_accept)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse deletePost(String token, Long Id) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse reportPost(String token, Long Id, String subject, String details)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse feel(String token, Long Id, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;



    GeneralResponse getMarkComment(GetMarkCommentReqDto getMarkCommentReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse setMarkComment(SetMarkCommentReqDto setMarkCommentReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse getListPosts(GetListPostsReqDto getListPostsReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;


    GeneralResponse search(SearchFunctionReqDto searchFunctionReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse getSavedSearch(GetSavedSearchReqDto getSavedSearchReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse delSavedSearch(DelSavedSearchReqDto delSavedSearchReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
}
