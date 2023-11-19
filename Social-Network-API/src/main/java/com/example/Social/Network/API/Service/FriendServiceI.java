package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface FriendServiceI {
    GeneralResponse getRequestedFriend(String token,Integer index , Integer count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
    GeneralResponse getListVideos() throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
    GeneralResponse getUserFriends(Long userId, String token, Integer index , Integer count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
    GeneralResponse setRequestedFriend(String token , Long userId) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
    GeneralResponse setAcceptFriend(String token , Long userId , String isAccept) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse setBlock(String token, Long userId,String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse getListBlocks(String token, Integer index, Integer count)throws ResponseException, ExecutionException, InterruptedException, TimeoutException;

    GeneralResponse getListSuggestedFriends(String token, Integer index, Integer count)throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
}
