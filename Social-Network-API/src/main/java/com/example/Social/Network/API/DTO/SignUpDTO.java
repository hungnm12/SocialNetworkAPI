package com.example.Social.Network.API.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class SignUpDTO {

    @Id
    private String id;
    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

    @JsonProperty
    private String uuid;
}
