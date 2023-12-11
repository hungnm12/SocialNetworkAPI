package com.example.Social.Network.API.Model.ResDto.PostResDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDetailResDto {
    private String content;
    private Date created;

    private Poster poster;

}
