package com.example.Social.Network.API.Model.ResDto.SearchResDto;

//import com.example.Social.Network.API.Model.Entity.Author;
import com.example.Social.Network.API.Model.Entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFunctionResDto {

    private Long Id;
    private Long name;
    private Video video;
    private String feel;
    private String mark_comment;
    private String is_felt;
//    private Author author;
    private String described;
}
