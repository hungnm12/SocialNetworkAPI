package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    public  String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER ;
    public boolean revoked;
    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public  UserEntity user;

    public void setId(Long id) {
        this.id = id;
    }

}
