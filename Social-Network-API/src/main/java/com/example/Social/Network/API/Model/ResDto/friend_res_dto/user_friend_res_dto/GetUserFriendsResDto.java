package com.example.Social.Network.API.Model.ResDto.friend_res_dto.user_friend_res_dto;

import com.example.Social.Network.API.Model.ResDto.friend_res_dto.request_friend_res_dto.GetRequestFriendResDetailDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserFriendsResDto {

    @JsonProperty
    ArrayList<GetUseFriendsResDetailDto> friends;

    @JsonProperty
    int total;


}
