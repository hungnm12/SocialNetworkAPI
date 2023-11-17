package com.example.Social.Network.API.Model.ResDto.account_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckVerifyCodeResDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    private boolean active;
}
