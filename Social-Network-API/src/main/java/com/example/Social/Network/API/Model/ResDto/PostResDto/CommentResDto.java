package com.example.Social.Network.API.Model.ResDto.PostResDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResDto {
    private Long id;
    private String mark_content;
    private  String typeOfMark;
    private Poster poster;
    private CommentDetailResDto comments;
    private Integer coins;

}
