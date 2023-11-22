package com.example.Social.Network.API.Model.Entity;

import com.example.Social.Network.API.Model.Entity.User;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author  {
    private String Id;
    private String name;
    private String coins;
    private String avatar;
    private String listings;


}
