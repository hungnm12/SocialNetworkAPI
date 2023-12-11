package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "search_id")
    private Long Id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "created")
    private Date created;

    @ManyToOne
    private User user;
}
