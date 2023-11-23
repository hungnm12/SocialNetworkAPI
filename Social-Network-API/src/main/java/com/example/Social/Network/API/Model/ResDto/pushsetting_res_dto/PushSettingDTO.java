package com.example.Social.Network.API.Model.ResDto.pushsetting_res_dto;


import com.example.Social.Network.API.Model.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushSettingDTO {
    private Long id;
    private String likeComment;
    private String fromFriends;
    private String requestedFriend;
    private String suggestedFriend;
    private String birthday;
    private String video;
    private String report;
    private String soundOn;
    private String notificationOn;
    private String vibrantOn;
    private String ledOn;
}