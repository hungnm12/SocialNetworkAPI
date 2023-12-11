package com.example.Social.Network.API.Model.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mark   {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User userMark;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date createdTime;
    @Column
    private String typeOfMark;

}
