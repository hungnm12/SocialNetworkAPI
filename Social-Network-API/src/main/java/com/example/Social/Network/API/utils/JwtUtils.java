package com.example.Social.Network.API.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

public class JwtUtils {

    private static final String SECRET_KEY = "mysecretkey";

    public static UserDetails extractUserDetailsFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build();

        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        String email = claims.getSubject();

        return new org.springframework.security.core.userdetails.User(
                email,
                "",
                new ArrayList<>()
        );
    }
}
