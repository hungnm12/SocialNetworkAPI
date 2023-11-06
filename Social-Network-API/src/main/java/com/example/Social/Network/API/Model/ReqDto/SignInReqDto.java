package com.example.Social.Network.API.Model.ReqDto;

import com.example.Social.Network.API.Model.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignInReqDto  extends UserEntity {
    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

}
