package com.example.Social.Network.API.Model.ResDto.account_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogInResDto {
    public LogInResDto(Long id, String email, String avatar, boolean active, Integer coins) {
        this.id = id;
        this.email = email;
        this.avatar = avatar;
        this.coins = coins;
    }
    public LogInResDto(Long id, boolean active) {
        this.id = id;

    }
    @JsonProperty
    private Long id;
    @JsonProperty
    private String email;

    @JsonProperty
    private String avatar;

    @JsonProperty
    private Integer coins;



    private String token;


}
