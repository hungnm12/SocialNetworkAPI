package com.example.Social.Network.API.Model.ResDto.account_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data

@AllArgsConstructor
public class GetUserInfoResDto extends UserResDto{


//    @Builder
    public GetUserInfoResDto(Long id,String username,String email,Date created,String avatar,   String coverImage, String link, String description, String address, String city, String country, String coins, int listing, boolean is_friend, boolean online) {
        super(id,username,email,created,avatar);
        this.coverImage = coverImage;
        this.link = link;
        this.description = description;
        this.address = address;
        this.city = city;
        this.country = country;
        this.coins = coins;
        this.listing = listing;
        this.is_friend = is_friend;
        this.online = online;
    }

    GetUserInfoResDto (){
        super();
    }
    @JsonProperty
    private String coverImage;
    @JsonProperty
    private String link;
    @JsonProperty
    private String description;
    @JsonProperty
    private String address;
    @JsonProperty
    private String city;
    @JsonProperty
    private String country;
    @JsonProperty
    private String coins;

    @JsonProperty
    private int listing;
    @JsonProperty
    private boolean is_friend;

    @JsonProperty
    private boolean online;


}
