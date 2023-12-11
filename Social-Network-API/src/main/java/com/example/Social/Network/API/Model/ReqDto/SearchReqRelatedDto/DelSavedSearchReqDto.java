package com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DelSavedSearchReqDto {

    private String token;
    private String search_id;
    private String all;
}
