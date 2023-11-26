package com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto;

import com.example.Social.Network.API.Model.ReqDto.SearchReqDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFunctionReqDto  {
    private String token;
    private String keyword;
    private Long Id;
    private Integer index;
    private Integer count;

}
