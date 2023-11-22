package com.example.Social.Network.API.Model.ResDto.PostResDto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetListPostsResDto {
    private Long Id;
    private String name;
    private String video;
    private String url;
    private String thumb;
    private String described;
    private Date created;
    private String feel;
    private String comment_mark;
    private String is_felt;
    private String is_blocked;
    private String can_edit;
    private String banned;
    private String status;
    private String author;

    private Long authorId;
    private String username;
    private String avatar;
    private String new_items;
    private String last_id;

}
