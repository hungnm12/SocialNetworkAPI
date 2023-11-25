package com.example.Social.Network.API.Model.ReqDto.PostReqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetMarkCommentReqDto {
    private String token;
    private Long id;
    private String comment;
    private Integer index;
    private Integer count;
    private Long markId;
    private String type;
}
