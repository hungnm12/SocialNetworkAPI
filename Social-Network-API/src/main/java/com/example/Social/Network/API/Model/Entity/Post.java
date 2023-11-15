package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Table(name = "post")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class Post implements Serializable {
    @Column(name = "post_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "described")
    private String described;

    @Column(name = "status")
    private String status;

    @Column(name = "post_url")
    private String url;

    @ManyToOne
    private User user;

    @Column(name = "video")
    private File video;

    @Column(name = "image")
    private File image;



}
