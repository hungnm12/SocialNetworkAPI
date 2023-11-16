package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
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
    public User user;

    public Token(Long id, String token, TokenType tokenType, boolean revoked, boolean expired, User user) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.revoked = revoked;
        this.expired = expired;
        this.user = user;
    }

    public Token() {

    }

    public void setId(Long id) {
        this.id = id;
    }

}
