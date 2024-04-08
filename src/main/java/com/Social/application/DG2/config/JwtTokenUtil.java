package com.Social.application.DG2.config;

import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenUtil{

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private UsersRepository usersRepository;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        Users user = usersRepository.findByUsername(username);
        if (user != null) {
            claims.put("userId", user.getId());
            claims.put("role", user.getRoleType());
        } else {
            throw new RuntimeException("User not found");
        }
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Boolean validateToken(String token) {
//        final String username = extractUsername(token);
        return !isTokenExpired(token) && !isTokenValid(token);
    }
//    check user with token valid but not used
public boolean isTokenValid(String token) {
        final String username = extractUsername(token);
        Users user = usersRepository.findByUsername(username);
        if (user != null) {
            final String expectedUsername = user.getUsername();
            return username.equals(expectedUsername);
        } else {
            return false;
        }
    }
    private Boolean isTokenExpired(String token)
    {
        Date expirationDate = extractExpiration(token);
        if (expirationDate == null) {
            return true;
        }
        Date currentDate = new Date();
        return expirationDate.before(currentDate);
    }
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }
    public String extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", String.class);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
