package com.example.Social.Network.API.Model.ResDto.PostResDto;

//import com.example.Social.Network.API.Model.Entity.Author;
import com.example.Social.Network.API.Model.Entity.Author;
import com.example.Social.Network.API.Model.Entity.Category;
import com.example.Social.Network.API.Model.Entity.Image;
import com.example.Social.Network.API.Model.Entity.Video;
import jakarta.persistence.Embedded;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetPostResDto {

    private Long Id;
    private String name;
    private String created;
    private String modified;
    private String trust;
    private String kudos;
    private String disappointed;
    private String isRated;
    private String isMarked;
    private String url;
    private String messages;
    private String fake;

    private List<Image> image;

    private List<Video> video;

//    @Embedded
//    private Image image;
//    @Embedded
//    private Video video;
    @Embedded
    private Author author;
    @Embedded
    private Category category;


    public void setImageUrls(List<String> imageUrls) {
    }
}
