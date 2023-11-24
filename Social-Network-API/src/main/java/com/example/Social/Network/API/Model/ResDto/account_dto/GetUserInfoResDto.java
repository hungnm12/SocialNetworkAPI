package com.example.Social.Network.API.Model.ResDto.account_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserInfoResDto {

        private Long id;
//        private String email;
        private String username;
        private Date created;

        private String description;
        private String avatar;
        private String coverImage;
        private String link;
        private String address;
        private String city;
        private String country;

        private String listing;
        private boolean is_friend;

        private String online;



}
