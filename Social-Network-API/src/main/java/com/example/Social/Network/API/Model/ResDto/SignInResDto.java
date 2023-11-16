package com.example.Social.Network.API.Model.ResDto;

//import com.example.Social.Network.API.Model.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class SignInResDto  {


    @jakarta.persistence.Id
    private Long Id;

    @JsonProperty
    private String username;


    @JsonProperty
    private String avatar;

    @JsonProperty
    private String active;

    @JsonProperty
    private String coins;


}
