package com.social.Social.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Since it is a assignment test project I am using it this way
    private static final String SECRET_KEY = "social_secret";

    /***
     * for extracting username from token
     * @param token jwt
     * @return string username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /***
     * extract expire date of token
     * @param token jwt token to check
     * @return date object of expired time
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /***
     * extract all the claims with claim resolver
     * @param token jwt
     * @param claimsResolver object of resolving values
     * @return object of claims with data
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /***
     * extract all the claims
     * @param token jwt
     * @return object of claims with data
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /***
     * check if token is expired or not
     * @param token jwt tobe checked
     * @return true if expired else false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /***
     * generate credentials for creating token
     * @param userDetails user cred
     * @return jwt token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /***
     * generate new token with given data
     * @param claims map of claims
     * @param subject string of values
     * @return jwt token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /***
     * validate token
     * @param token string jwt
     * @param userDetails against which it is to be validated
     * @return true if valid else false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}