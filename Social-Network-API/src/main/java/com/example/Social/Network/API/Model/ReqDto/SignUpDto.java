package com.example.Social.Network.API.Model.ReqDto;

import com.example.Social.Network.API.Model.Entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignUpDto {
    @JsonProperty
    @Column(name = "email")
    private String email;

    @JsonProperty
    @Column(name = "password")
    private String password;

    @JsonProperty
    @Column(name = "uuid")
    private String uuid;
}
