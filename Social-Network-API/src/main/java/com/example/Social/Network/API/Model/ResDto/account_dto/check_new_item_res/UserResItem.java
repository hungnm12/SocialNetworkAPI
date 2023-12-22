package com.example.Social.Network.API.Model.ResDto.account_dto.check_new_item_res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResItem {
    @JsonProperty
    private String id;
    @JsonProperty
    private String active;

    public UserResItem(String id, String active) {
        this.id=id;
        this.active=active;
    }
}
