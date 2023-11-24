package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.Impl.FriendServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/friend")
public class FriendController {

    @Autowired
    private final FriendServiceImpl friendService;

    public FriendController(FriendServiceImpl friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/setRequestedFriend")
    public GeneralResponse setRequestedFriend(@RequestParam String token, @RequestParam Long userId) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return friendService.setRequestedFriend(token,userId );
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/setAcceptFriend")
    public GeneralResponse setAcceptFriend(@RequestParam String token, @RequestParam Long userId, @RequestParam String isActive) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return friendService.setAcceptFriend(token,userId,isActive );
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/getRequestedFriend")
    public GeneralResponse getRequestedFriend(@RequestParam String token, @RequestParam Integer index, @RequestParam Integer count) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return friendService.getRequestedFriend(token,index, count );
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/get_user_friends")
    public GeneralResponse getUserFriends(@RequestParam(required = false) Long userId , @RequestParam String token, @RequestParam Integer index, @RequestParam Integer count)  throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return friendService.getUserFriends(userId, token,index,count );
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/set_block")
    public GeneralResponse setBlock(@RequestParam String token , @RequestParam(required = false) Long userId, @RequestParam String type) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        try {
            return friendService.setBlock(token, userId,type);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }
    }
    @PostMapping("/get_list_blocks")
    public GeneralResponse getListBlocks(@RequestParam String token, @RequestParam Integer index, @RequestParam Integer count)  throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException{
        try {
            return friendService.getListBlocks(token, index, count);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }
    }

    @PostMapping("/get_list_suggested_friends")
    public GeneralResponse getListSuggestedFriends(@RequestParam String token, @RequestParam Integer index, @RequestParam Integer count)  throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException{
        try {
            return friendService.getListSuggestedFriends(token, index, count);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }
    }

}
