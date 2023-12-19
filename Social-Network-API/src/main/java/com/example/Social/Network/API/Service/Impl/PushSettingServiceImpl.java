package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.PushSetting;
import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Model.ResDto.pushsetting_res_dto.PushSettingDTO;
import com.example.Social.Network.API.Repository.PushSettingRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.PushSettingServiceI;
import com.example.Social.Network.API.utils.CheckUtils;
import com.example.Social.Network.API.utils.JwtUtils;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
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
    final ModelMapper modelMapper = new ModelMapper();

    public PushSettingServiceImpl(PushSettingRepo pushSettingRepo, JwtService jwtService, UserRepo userRepo) {
        this.pushSettingRepo = pushSettingRepo;
        this.jwtService = jwtService;
        this.userRepo = userRepo;

    }

    @Override
    public GeneralResponse getPushSettings(String token) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        var user = JwtUtils.getUserFromToken(jwtService,userRepo, token);
        if(!jwtService.isTokenValid(token,user) || token==null)
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"The Token is not valid");

        }
        if(!user.isActive())
        {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED,"The user has been block by the system");

        }
        PushSetting existPushSetting = pushSettingRepo.findPushSettingByUser(user);




        if(existPushSetting==null)
        {
            PushSetting pushSetting = getPushSetting(user);
            pushSettingRepo.save(pushSetting);
            PushSettingDTO orderDTO = modelMapper.map(pushSetting, PushSettingDTO.class);
            return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, orderDTO);
        }

        PushSettingDTO orderDTO = modelMapper.map(existPushSetting, PushSettingDTO.class);
            return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,orderDTO);



    }

    @NotNull
    private static PushSetting getPushSetting(User user) {
        PushSetting pushSetting = new PushSetting();
        pushSetting.setUser(user);
        pushSetting.setLikeComment("1");
        pushSetting.setFromFriends("1");
        pushSetting.setRequestedFriend("1");
        pushSetting.setSuggestedFriend("1");
        pushSetting.setBirthday("1");
        pushSetting.setVideo("1");
        pushSetting.setReport("1");
        pushSetting.setSoundOn("1");
        pushSetting.setNotificationOn("1");
        pushSetting.setVibrantOn("1");
        pushSetting.setLedOn("1");
        return pushSetting;
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

        for (String s : input) {
            if( s== null)
            {
                countUndefinedInputs++;

            }
            else if ( (!s.equals("0") && !s.equals("1"))) {
                System.out.println(s);
                isValidInput = false;
                break;
            }

        }
        if (!isValidInput || countUndefinedInputs == input.length) {
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
        if(previousPushSetting== null)
        {
            pushSettingRepo.save(pushSetting);
            return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,"Ok");

        }
        else{
                pushSetting.setId(previousPushSetting.getId());
                DiffNode diff = ObjectDifferBuilder.buildDefault().compare(previousPushSetting, pushSetting);
                if(!diff.hasChanges())
                {
                    return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE,"There are no changes");

                }


            System.out.println("3");
            previousPushSetting.setUser(user);
            previousPushSetting.setLikeComment(likeComment);
            previousPushSetting.setFromFriends(fromFriends);
            previousPushSetting.setRequestedFriend(requestedFriend);
            previousPushSetting.setSuggestedFriend(suggestedFriend);
            previousPushSetting.setBirthday(birthday);
            previousPushSetting.setVideo(video);
            previousPushSetting.setReport(report);
            previousPushSetting.setSoundOn(soundOn);
            previousPushSetting.setNotificationOn(notificationOn);
            previousPushSetting.setVibrantOn(vibrantOn);
            previousPushSetting.setLedOn(ledOn);
            pushSettingRepo.save(previousPushSetting);
            return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,"Update success");
        }


//        if(previousPushSetting.equals(pushSetting))
//        {
//            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE,"Action has done previously by this user");
//
//        }



    }

}
