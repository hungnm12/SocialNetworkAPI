package com.example.Social.Network.API.Model.ResDto.PostResDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data

public class GetListPostsResDto {
    private Long Id;
    private String name;
    private MultipartFile video;
    private String url;
    private MultipartFile thumb;
    private String described;
    private Date created;
    private String feel;
    private String comment_mark;
    private boolean is_felt;
    private boolean is_blocked;
    private boolean can_edit;
    private String banned;
    private String status;
    private String author;
    private Long authorId;
    private String username;
    private String avatar;
    private String new_items;
    private String last_id;

}
