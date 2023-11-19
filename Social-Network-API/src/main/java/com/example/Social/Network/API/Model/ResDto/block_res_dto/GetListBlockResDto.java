package com.example.Social.Network.API.Model.ResDto.block_res_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetListBlockResDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String username;

    @JsonProperty
    private String avatar;
}
