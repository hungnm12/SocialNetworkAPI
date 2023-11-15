package com.example.Social.Network.API.Model.ReqDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignUpReqDto {
    @JsonProperty
    private String email;

    @JsonProperty
    private String password;
//
//    @JsonProperty
//    private String uuid;
}
