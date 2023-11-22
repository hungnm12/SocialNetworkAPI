package com.example.Social.Network.API.Model.ResDto.PostResDto;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMarkCommentResDto {

    private String id;
    private String mark_content;
    private String typeOfMark;

//    @Embedded
//    private Poster poster;

//    @Embedded
//    private Comment comment;



}
