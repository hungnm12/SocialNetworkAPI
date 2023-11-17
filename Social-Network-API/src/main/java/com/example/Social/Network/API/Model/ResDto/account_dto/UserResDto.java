package com.example.Social.Network.API.Model.ResDto.account_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResDto {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty
    private Date created;

    @JsonProperty
    private String avatar;

}
