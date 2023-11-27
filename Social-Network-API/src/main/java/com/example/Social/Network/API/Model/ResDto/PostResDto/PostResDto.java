package com.example.Social.Network.API.Model.ResDto.PostResDto;

import com.example.Social.Network.API.Model.Entity.Author;
import com.example.Social.Network.API.Model.Entity.Image;
import com.example.Social.Network.API.Model.Entity.Video;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResDto {

    private String Id;
    private String name;
    private String described;
    private String created;
    private String feel;
    private String comment_mark;
    private String is_felt;
    private String is_blocked;
    private String can_edit;
    private String banned;
    private String status;

    @Embedded
    private Image image;
    @Embedded
    private Video video;
    @Embedded
    private Author author;


}
