package com.example.Social.Network.API.Model.ResDto.ConversationResDto;

import com.example.Social.Network.API.Model.Entity.LastMessage;
import com.example.Social.Network.API.Model.Entity.Partner;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListConversationResDto {
    private String Id;

    @Embedded
    private Partner partner;

    @Embedded
    private LastMessage lastMessage;
}
