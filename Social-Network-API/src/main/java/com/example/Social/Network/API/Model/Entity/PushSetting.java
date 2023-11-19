package com.example.Social.Network.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "push_setting")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PushSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "like_comment")
    private String likeComment;

    @Column(name = "from_friends")
    private String fromFriends;

    @Column(name = "requested_friend")
    private String requestedFriend;

    @Column(name = "suggested_friend")
    private String suggestedFriend;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "video")
    private String video;

    @Column(name = "report")
    private String report;

    @Column(name = "sound_on")
    private String soundOn;

    @Column(name = "notification_on")
    private String notificationOn;

    @Column(name = "vibrant_on")
    private String vibrantOn;

    @Column(name = "led_on")
    private String ledOn;
}
