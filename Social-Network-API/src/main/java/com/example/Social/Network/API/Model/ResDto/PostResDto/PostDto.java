package com.example.Social.Network.API.Model.ResDto.PostResDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long Id;

    private String url;

    private Integer coins;

    public PostDto(Long id, String url) {
        Id = id;
        this.url = url;
    }
}
