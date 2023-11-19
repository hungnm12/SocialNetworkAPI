package com.example.Social.Network.API.Service;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface PushSettingServiceI {
    GeneralResponse getPushSettings(String token) throws ResponseException, ExecutionException, InterruptedException, TimeoutException;
     GeneralResponse setPushSetting(String token, String likeComment, String fromFriends, String requestedFriend,
                                    String suggestedFriend, String birthday, String video, String report, String soundOn,
                                    String notificationOn, String vibrantOn, String ledOn) throws ResponseException, ExecutionException, InterruptedException, TimeoutException ;

}
