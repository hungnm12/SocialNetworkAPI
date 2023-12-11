package com.example.Social.Network.API.Model.ReqDto.ConversationReqDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListConversationReqDto {
    private String token;
    private Integer count;
    private Integer index;
}
