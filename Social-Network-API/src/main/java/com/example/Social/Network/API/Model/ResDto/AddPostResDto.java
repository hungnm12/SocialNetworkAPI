package com.example.Social.Network.API.Model.ResDto;

import com.example.Social.Network.API.Model.Entity.User;
import jakarta.persistence.Id;

public class AddPostResDto {
    @Id
    private User Id;

    private String url;

    private User coins;
}
