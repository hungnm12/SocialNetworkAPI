package com.example.Social.Network.API.Model.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Table(name = "user")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class User implements Serializable, UserDetails {
    @Column
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @Column(name = "email")
    private String email;

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
    private LocalDateTime created;

    @JsonProperty
    @Column(name= "active_account")
    private boolean active= false;

    @Column(name = "coins")
    private Integer coins;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

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

    private void setDefaultAvatar(){
        this.avatar = "";
    }
}
