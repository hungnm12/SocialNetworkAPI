package com.example.Social.Network.API.Service.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long expiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Value("${application.security.jwt.verify-token.expiration}")
    private long verifyToken;

    public <T> T extractClaim(String token, Function<Claims,T> claimsFunction){
        final  Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }
    public String generateVerifyToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, verifyToken);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails)
    {
        return buildToken(extraClaims,userDetails, expiration);
    }
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration)
    {
      return Jwts.builder().setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + expiration) )
//              sign the JWT using the specified signing key and algorithm .This generates the signature
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
    };


    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractCreateAt(String token){
        return extractClaim(token, Claims::getIssuedAt);
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired( token));
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
//    This is the key we use to create the signature to verify and toan ven du lieux
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(secretKey.getBytes());
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
//                Set the signing key to verify the JWT digital signature
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

