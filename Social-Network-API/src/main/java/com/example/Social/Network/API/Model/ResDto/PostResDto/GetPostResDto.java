package com.example.Social.Network.API.Model.ResDto.PostResDto;

import com.example.Social.Network.API.Model.Entity.Author;
import com.example.Social.Network.API.Model.Entity.Category;
import com.example.Social.Network.API.Model.Entity.Image;
import com.example.Social.Network.API.Model.Entity.Video;
import lombok.Data;

import java.util.Date;

@Data
public class GetPostResDto {

    private Long Id;
    private String name;
    private Date created;
    private Date modified;
    private Long trust;
    private Long kudos;
    private Long disappointed;
    private Boolean isRated;
    private Boolean isMarked;
    private String url;
    private String messages;
    private Long fake;


    private Image image;
    private Video video;
    private Author author;
    private Category category;

    public static class Image extends com.example.Social.Network.API.Model.Entity.Image {
    }
    public static class Video extends com.example.Social.Network.API.Model.Entity.Video {

    }
    public static class Author extends com.example.Social.Network.API.Model.Entity.Author {

    }

    public static class Category extends com.example.Social.Network.API.Model.Entity.Category {

    }
}
