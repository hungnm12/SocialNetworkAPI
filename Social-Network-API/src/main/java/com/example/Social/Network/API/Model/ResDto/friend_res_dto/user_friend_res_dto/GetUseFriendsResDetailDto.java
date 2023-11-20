package com.example.Social.Network.API.Model.ResDto.friend_res_dto.user_friend_res_dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class GetUseFriendsResDetailDto {

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
