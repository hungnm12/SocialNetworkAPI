package com.example.Social.Network.API.Model.ResDto.SearchResDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSavedSearchResDto {
    private String Id;
    private String keyword;
    private String created;
}
