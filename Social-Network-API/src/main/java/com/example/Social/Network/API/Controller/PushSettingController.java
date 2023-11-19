package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.Impl.PushSettingServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("/api/v1/pushSetting")
public class PushSettingController {
    private PushSettingServiceImpl pushSettingService;
    @PostMapping("/getPushSettings")
    public GeneralResponse getPushSettings(@RequestParam String token) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return pushSettingService.getPushSettings(token);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/setPushSetting")
    public GeneralResponse setPushSetting(String token, String likeComment, String fromFriends, String requestedFriend,
                                           String suggestedFriend, String birthday, String video, String report, String soundOn,
                                           String notificationOn, String vibrantOn, String ledOn) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return pushSettingService.setPushSetting(token,likeComment,fromFriends,requestedFriend,suggestedFriend, birthday,video,report,soundOn,notificationOn,vibrantOn,ledOn);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
}
