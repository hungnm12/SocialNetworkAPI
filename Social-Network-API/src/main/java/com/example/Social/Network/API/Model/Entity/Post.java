package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "post")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class Post implements Serializable {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "described")
    private String described;

    @Column(name = "status")
    private String status;

    @Column(name = "post_url")
    private String url;

    @Column(name = "created")
    private Date created;

    @ManyToOne
    private User user;

    @Column(name = "video")
    @OneToMany(mappedBy = "post")
    private ArrayList<Video> videos;

    @Column(name = "image")
    @OneToMany
    private ArrayList<Image> images ;

    @Column(name = "subject")
    private String subject;

    @Column(name = "details")
    private String details;

    @Column(name = "isReported")
    private boolean isReported = false;



}
