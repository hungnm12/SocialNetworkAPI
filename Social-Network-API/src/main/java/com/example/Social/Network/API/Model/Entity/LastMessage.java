package com.example.Social.Network.API.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastMessage {
        private String message;
        private String created;
        private String unread;

}
