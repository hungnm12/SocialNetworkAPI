package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable

public class Sender {
    private String id;
    private String username;
    private String avatar;
}
