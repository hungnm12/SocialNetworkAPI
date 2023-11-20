package com.example.Social.Network.API.Model.ReqDto.PostReqDto;

import com.example.Social.Network.API.Model.ReqDto.SearchReqDto;
import lombok.Data;

@Data
public class GetListPostsReqDto extends SearchReqDto {
    private String token;
    private Long Id;
    private String inCampaign;
    private String campaignId;
    private String latitude;
    private String longitude;
    private String lastId;




}
