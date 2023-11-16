package com.example.Social.Network.API.Model.ReqDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class SignInReqDto {
    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

}
