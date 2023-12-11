package com.example.Social.Network.API.Model.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Table(name = "user")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Builder
@Embeddable
public class User implements Serializable, UserDetails {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @Column(name = "email")
    private String email;

    @JsonProperty
    @Column(name = "user_name")
    private String username;
    @JsonProperty
    @Column(name = "password")
    private String password;

    @JsonProperty
    @Column(name = "uuid")
    private String uuid;

    @JsonProperty
    @Column(name = "avatar")
    private String avatar;

    @JsonProperty
    @Column(name = "create_date")
    private Date created;

    @JsonProperty
    @Column(name= "active_account")
    private boolean active= false;

    @Column(name = "coins")
    private Integer coins;

    @OneToMany(mappedBy = "user")
    private List<Post> listing;

    @OneToMany(mappedBy = "user")
    private List<Search> search;

    @OneToMany(mappedBy = "user")
    private List<Conversation> conversation;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column
    private String description;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String link;
    @Column
    private String coverImage;
    @Column(name = "online_user")
    private String online= "0";


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "User";
            }
        });
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return active;
    }
    public String getUserNameAccount(){
        return this.username;
    }
    public void setUserNameAccount(String username){
     this.username = username;
    }

}
