package com.example.bankingservice.configurations;

import com.example.bankingservice.domain.base.User;
import com.example.bankingservice.domain.utils.CustomException;
import com.example.bankingservice.domain.utils.UserFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 3600000; // 1 hour

    public static String generateToken(UserDetails userDetails) throws Exception {
        String userRole = userDetails.getAuthorities().toArray()[0].toString();
        User user = UserFactory.getUser(userDetails.getUsername(), userRole);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        Map<String, Object> map = user.toMap();
        Claims claims = Jwts.claims(Jwts.claims(map)
                .setExpiration(expiration));

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setClaims(claims)
                .setSubject((String) map.get("phoneNumber"))
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256);
        return builder.compact();
    }

    public static Claims decodeToken(String token) throws CustomException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key.getEncoded())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Invalid.jwt.token");
        }

    }

    public boolean validate(String token) throws CustomException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Invalid.jwt.token");
        }
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
