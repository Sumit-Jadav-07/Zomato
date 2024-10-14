package com.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class GenerateToken {

    // Securely generate a random key for signing the token
    private static final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    // Method to create a JWT token with a subject and an expiration time
    public String createToken(String subject) {
        // Set token expiration time to 1 hour (or customize as needed)
        long expirationTime = 1000 * 60 * 60;

        return Jwts.builder()
            .setSubject(subject)                      // Add the subject (e.g., user identifier)
            .setIssuedAt(new Date())                  // Set the current date as the issue date
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Set token expiration
            .signWith(key)                            // Sign the token with the secret key
            .compact();                               // Build and compact the JWT into a string
    }

}
