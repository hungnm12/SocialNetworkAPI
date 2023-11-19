package com.example.Social.Network.API.Model.ResDto.friend_res_dto.request_friend_res_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SetFriendRequestRes {
    @JsonProperty
    Long request_friend;

}
