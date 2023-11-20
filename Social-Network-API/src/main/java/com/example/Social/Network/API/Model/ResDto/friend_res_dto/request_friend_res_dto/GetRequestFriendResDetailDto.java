package com.example.Social.Network.API.Model.ResDto.friend_res_dto.request_friend_res_dto;

import com.example.Social.Network.API.Model.Entity.FriendRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestFriendResDetailDto {

    @JsonProperty
    Long id;
    @JsonProperty
    String username;
    @JsonProperty
    String avatar;
    @JsonProperty
    Long sameFriends;
//    Time to receive requests
    @JsonProperty
    Date created;




}
