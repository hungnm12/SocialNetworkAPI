package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@Entity
@AllArgsConstructor
public class Video {

    @Id
    @Column(name="video_id")
    private long id;


    @Column(name = "url_video")
    private String urlVideo;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;
    public Video() {

    }

    public Video(String url, Post post1) {
    }
}
