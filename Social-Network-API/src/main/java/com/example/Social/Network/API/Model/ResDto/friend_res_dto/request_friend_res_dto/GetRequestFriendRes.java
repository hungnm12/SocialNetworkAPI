package com.example.Social.Network.API.Model.ResDto.friend_res_dto.request_friend_res_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
public class GetRequestFriendRes {
    @JsonProperty
    ArrayList<GetRequestFriendResDetailDto> request;
    @JsonProperty
    Long total;

}
