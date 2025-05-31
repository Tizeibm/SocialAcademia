package com.example.SocialAcademia.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

   @Value("${jwt.expiration}")
    private Long expiration;

   private Key getSigningKey(){
       return Keys.hmacShaKeyFor(secret.getBytes());
   }

   public String extractEmail(String token){
       return Jwts.parserBuilder()
               .setSigningKey(getSigningKey())
               .build()
               .parseClaimsJws(token)
               .getBody()
               .getSubject();
   }

   public String generateToken(String email){
       return Jwts.builder()
               .setSubject(email)
               .setIssuedAt(new Date())
               .signWith(getSigningKey(), SignatureAlgorithm.HS256)
               .compact();
   }

}
