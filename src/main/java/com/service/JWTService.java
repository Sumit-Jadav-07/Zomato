package com.service;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

  private String secretKey = "";

  public JWTService() {
    try {
      KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
      SecretKey sk = keyGen.generateKey();
      secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error initializing JWT Key ", e);
    }
  }

  public String generateToken(String email, String role) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", role);
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour validity
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(getKey())
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      System.out.println("Invalid or expired token: " + e.getMessage());
      return false;
    }
  }

  public String validateTokeAndGetEmail(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public String validateTokenAndGetRole(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.get("role", String.class); // Extract the role
  }

}
