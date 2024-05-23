package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JWTService extends SuperService {
    String generateToken(String userName);
    String extractUsername(String token);
    Date extractExpiration(String token);
    <T>T extractClaim(String token, Function<Claims,T> claimsResolver);
    boolean validateToken(String token, UserDetails userDetails);
}
