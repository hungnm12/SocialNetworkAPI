package com.example.Social.Network.API.Model.ResDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignUpResDto {

    @JsonProperty
    private String email;

    @JsonProperty
    private String password;



}
