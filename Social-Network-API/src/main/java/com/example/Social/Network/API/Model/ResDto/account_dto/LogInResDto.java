package com.example.Social.Network.API.Model.ResDto.account_dto;

//import com.example.Social.Network.API.Model.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LogInResDto {

    @JsonProperty

    private Long userId;



    @JsonProperty
    private boolean active;




}
