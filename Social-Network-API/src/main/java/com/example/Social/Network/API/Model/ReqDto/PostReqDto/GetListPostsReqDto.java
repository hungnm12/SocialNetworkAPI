package com.example.Social.Network.API.Model.ReqDto.PostReqDto;

import lombok.Data;

@Data
public class GetListPostsReqDto {
    private String token;
    private Long Id;
    private String inCampaign;
    private String latitude;
    private String longitude;
    private String lastId;
    private String index;
    private String count;



}
