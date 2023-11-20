package com.example.Social.Network.API.Model.ReqDto.PostReqDto;

import com.example.Social.Network.API.Model.ReqDto.SearchReqDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMarkCommentReqDto extends SearchReqDto {
    private Long Id;
}

