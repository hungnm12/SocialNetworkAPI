package com.example.Social.Network.API.utils;

import com.example.Social.Network.API.Model.Entity.User;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.Impl.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;

public class JwtUtils {

    public static User getUserFromToken(JwtService jwtService, UserRepo userRepo, String token) {
        String  username = jwtService.extractUsername(token);

        return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));

    }
    public static Date getCreateAt(JwtService jwtService,  String token) {

        return jwtService.extractCreateAt(token);
    }


}
