package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Image {
    @Id
    @Column(name = "image_id")
    private long id;

    @Column(name = "url_image")
    private String urlImage;
//
//    @ManyToOne()
//    @JoinColumn(name = "post_id")
//    private Post post;
    public Image() {

    }

    public Image(String url, Post post1) {
    }
}
