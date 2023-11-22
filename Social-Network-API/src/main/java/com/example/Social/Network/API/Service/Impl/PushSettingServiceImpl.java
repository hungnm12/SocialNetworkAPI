package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.PushSetting;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Repository.PushSettingRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.PushSettingServiceI;
import com.example.Social.Network.API.utils.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Transactional
public class PushSettingServiceImpl implements PushSettingServiceI {
    @Autowired
    private final PushSettingRepo pushSettingRepo;
    @Autowired
    private  final JwtService jwtService;

    @Autowired
    private  final UserRepo userRepo;

    public PushSettingServiceImpl(PushSettingRepo pushSettingRepo, JwtService jwtService, UserRepo userRepo) {
        this.pushSettingRepo = pushSettingRepo;
        this.jwtService = jwtService;
        this.userRepo = userRepo;

    }

    @Override
    public GeneralResponse getPushSettings(String token) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse setPushSetting(String token, String likeComment, String fromFriends, String requestedFriend, String suggestedFriend, String birthday, String video, String report, String soundOn, String notificationOn, String vibrantOn, String ledOn) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        String[] input = {
                likeComment,
                fromFriends,
                requestedFriend,
                suggestedFriend,
                birthday,
                video,
                report,
                soundOn,
                notificationOn,
                vibrantOn,
                ledOn
        };

        boolean isValidInput = true;
        int countUndefinedInputs = 0;

        for (int i = 0; i < input.length; i++) {
            if (input[i] != null
                    && !input[i].equals("0")
                    && !input[i].equals("1")) {
                isValidInput = false;
                break;
            }

            if (input[i] == null) {
                countUndefinedInputs++;
            }
        }
        if (countUndefinedInputs == input.length) {
            isValidInput = false;
        }
        if (!isValidInput) {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The parameter is not enough or not valid");

        }
        var user = JwtUtils.getUserFromToken(jwtService,userRepo,token);
        if(!user.isActive())
        {
            return new GeneralResponse(ResponseCode.NOT_ACCESS, ResponseMessage.NOT_ACCESS,"The user is blocked from System");
        }
        PushSetting previousPushSetting = pushSettingRepo.findPushSettingByUser(user);

        PushSetting pushSetting = new PushSetting();
        pushSetting.setUser(user);
        pushSetting.setLikeComment(likeComment);
        pushSetting.setFromFriends(fromFriends);
        pushSetting.setRequestedFriend(requestedFriend);
        pushSetting.setSuggestedFriend(suggestedFriend);
        pushSetting.setBirthday(birthday);
        pushSetting.setVideo(video);
        pushSetting.setReport(report);
        pushSetting.setSoundOn(soundOn);
        pushSetting.setNotificationOn(notificationOn);
        pushSetting.setVibrantOn(vibrantOn);
        pushSetting.setLedOn(ledOn);
        if(previousPushSetting.equals(pushSetting))
        {
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE,"Action has done previously by this user");

        }
        pushSettingRepo.save(pushSetting);
        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,"Ok");



    }
}
