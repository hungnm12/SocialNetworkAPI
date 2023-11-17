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
        this.username = email;
        this.avatar = avatar;
        this.coins = coins;
    }

    @JsonProperty
    private Long id;
    @JsonProperty
    private String username;

    @JsonProperty
    private String token;
    @JsonProperty
    private String avatar;


    @JsonProperty
    private boolean active;
    @JsonProperty
    private Integer coins;




}
