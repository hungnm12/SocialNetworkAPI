package com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSavedSearchReqDto {
    private String token;
    private Integer index;
    private Integer count;
}
