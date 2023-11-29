package com.example.Social.Network.API.Model.ReqDto.ConversationReqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteConversationReqDto {
        private String token;
        private String search_id;
        private String all;

}
