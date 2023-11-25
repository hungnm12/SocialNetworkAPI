package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;


import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date createdTime;

    // Getters and Setters

}