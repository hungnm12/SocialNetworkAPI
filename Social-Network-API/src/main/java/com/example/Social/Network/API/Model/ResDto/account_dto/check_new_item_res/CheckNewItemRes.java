package com.example.Social.Network.API.Model.ResDto.account_dto.check_new_item_res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckNewItemRes {
    @JsonProperty
    private Version version;
    @JsonProperty
    private UserResItem user;
    @JsonProperty
    private String badge;
    @JsonProperty

    private String unread_message;
    @JsonProperty

    private String now;
}
