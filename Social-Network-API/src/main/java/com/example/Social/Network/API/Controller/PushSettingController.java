package com.example.Social.Network.API.Controller;

import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.Impl.PushSettingServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PushSettingController {

    public PushSettingController(PushSettingServiceImpl pushSettingService) {
        this.pushSettingService = pushSettingService;
    }

    @Autowired
    private PushSettingServiceImpl pushSettingService;
    @PostMapping("/get_push_settings")
    public GeneralResponse getPushSettings(@RequestParam String token) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return pushSettingService.getPushSettings(token);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
    @PostMapping("/set_push_settings")
    public GeneralResponse setPushSetting(String token, @RequestParam(required = false) String like_comment, @RequestParam(required = false)  String from_friends,
                                          @RequestParam(required = false)  String requested_friend,
                                          @RequestParam(required = false)  String suggested_friend, @RequestParam(required = false)  String birthday,
                                          @RequestParam(required = false)  String video, @RequestParam(required = false)  String report, String sound_on,
                                          @RequestParam(required = false)  String notification_on, @RequestParam(required = false)  String vibrant_on, @RequestParam(required = false)  String led_on) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        try {
            return pushSettingService.setPushSetting(token, like_comment, from_friends, requested_friend, suggested_friend, birthday,video,report,sound_on, notification_on, vibrant_on, led_on);
        }
        catch (ResponseException e) {
            return new GeneralResponse(HttpsURLConnection.HTTP_NO_CONTENT, "" , e.getMessage(), null);
        }

    }
}
