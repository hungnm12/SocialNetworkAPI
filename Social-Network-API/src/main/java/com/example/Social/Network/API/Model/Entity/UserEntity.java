package com.example.Social.Network.API.Model.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Table(name = "user")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class UserEntity implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JsonProperty
    @Column(name = "email")
    private String email;

    @JsonProperty
    @Column(name = "password")
    private String password;

    @JsonProperty
    @Column(name = "uuid")
    private String uuid;

    @JsonProperty
    @Column(name = "avatar")
    private String avatar;

    @JsonProperty
    @Column(name = "create_date")
    private Date created;

    @JsonProperty
    @Column(name = "active")
    private String active;

    @JsonProperty
    @Column(name = "coins")
    private String coins;

    @JsonProperty
    @Column(name = "username")
    private String username;


}
