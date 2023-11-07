package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "image")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class Image {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "url")
    private String url;
}
