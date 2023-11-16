package com.example.Social.Network.API.Model.ResDto;

import com.example.Social.Network.API.Model.Entity.Post;
import com.example.Social.Network.API.Model.Entity.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPostResDto {

    private Long Id;

    private String url;

    private Integer coins;


}
