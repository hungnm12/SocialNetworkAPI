package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.PushSetting;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Service.PushSettingServiceI;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class PushSettingServiceImpl implements PushSettingServiceI {

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
        PushSetting pushSetting = new PushSetting();
//        pushSetting.set


        return null;
    }
}
