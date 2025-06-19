package com.ueprojet.appSignalement.authentification;

//import java.util.HashMap;
//import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import org.springframework.stereotype.Component;
import java.util.Date;


@Component 
public class JwtUtil { 

  // private String SECRET_KEY = "yourSecretKey"; 
   private static final String SECRET = "MY_SECRET_KEY_256_BITS_LONG_EXAMPLE";  // Doit être >= 32 caractères

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

   public String generateToken(String username, Long userId) {
    Claims claims = Jwts.claims();
    claims.put("username", username);
    claims.put("id", userId);  
    return Jwts.builder() 
            .setSubject(username) 
            .setIssuedAt(new Date(System.currentTimeMillis())) 
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
            .signWith( SECRET_KEY , SignatureAlgorithm.HS256) 
            .compact();
   } 

   public String createToken(String username, Long userId, Long arrondissementId) {
    Claims claims = Jwts.claims();
    claims.put("username", username);
    claims.put("id", userId);
    claims.put("arrondissementId", arrondissementId);
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
}

public String createToken(String username, Long userId, String email, Long arrondissementId) {
    Claims claims = Jwts.claims();
    claims.put("username", username);
    claims.put("id", userId);
    claims.put("email", email);
    claims.put("arrondissementId", arrondissementId);
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
}

  /*   private String createToken(String subject) { 
       return Jwts.builder() 
               .setClaims(claims) 
               .setSubject(subject) 
               .setIssuedAt(new Date(System.currentTimeMillis())) 
               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
               .signWith(SignatureAlgorithm.HS256, SECRET_KEY) 
               .compact(); 
   } */

   public boolean validateToken(String token, String username) { 
       final String extractedUsername = extractUsername(token); 
       return (extractedUsername.equals(username) && !isTokenExpired(token)); 
   } 

   public String extractUsername(String token) { 
       return extractAllClaims(token).getSubject(); 
   } 

   private Claims extractAllClaims(String token) { 
        return Jwts.parserBuilder() // Utilisation de parserBuilder()
            .setSigningKey(SECRET_KEY) // Configuration de la clé de signature
            .build() // Construction du parser
            .parseClaimsJws(token) 
            .getBody(); 
    } 

   private boolean isTokenExpired(String token) { 
       return extractAllClaims(token).getExpiration().before(new Date()); 
   } 
}
