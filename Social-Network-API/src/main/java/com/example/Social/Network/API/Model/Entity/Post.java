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
@Embeddable
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

    @Column(name = "modified")
    private Date modified;

    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "post")
    private List<Video> videos;


    @OneToMany(mappedBy = "post")
    private List<Image> images ;


//    @Embedded
//    private  Advertisement advertisement;

    @Column(name = "subject")
    private String subject;

    @Column(name = "fake")
    private String fake;

    @Column(name = "trust")
    private String trust;

    @Column(name = "kudos")
    private Long kudos;

    @Column(name = "dissapointed")
    private Long dissapointed;

    @Column(name = "details")
    private String details;

    @Column(name = "isReported")
    private boolean isReported = false;

    @Column(name = "isRated")
    private boolean isRated = false;

    @Column(name = "isMarked")
    private boolean isMarked = false;

    @Column(name = "last_id")
    private String lastId;

    @Column(name = "isBanned")
    private String banned = "0";



}
