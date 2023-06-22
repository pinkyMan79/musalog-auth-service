package one.terenin.security.util.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import one.terenin.dto.response.UserResponse;
import one.terenin.security.propertysource.JWTPropertySource;
import one.terenin.security.util.JwtUtil;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtilImpl implements JwtUtil {

    private final JWTPropertySource propertySource;

    @Override
    public Map<String, String> generateTokens(String subject, String authority, UUID userId) {
        Date createdDate = new Date();
        Date expAccess = new Date(new Date().getTime() + propertySource.getJwtExpiration() * 1000L);
        Date expRefresh = new Date(new Date().getTime() + propertySource.getJwtExpirationRefresh() * 1000L);
        Map<String, Object> claims = new HashMap<>();
        claims.put("authority", authority);
        claims.put("userId", userId);
        claims.put("subject", subject);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(propertySource.getJwtIssuer())
                .setIssuedAt(createdDate)
                .setExpiration(expAccess)
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(propertySource.getJwtSecret().getBytes()))
                .compact();
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(propertySource.getJwtIssuer())
                .setIssuedAt(createdDate)
                .setExpiration(expRefresh)
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(propertySource.getJwtSecret().getBytes()))
                .compact();
        Map<String, String> refreshToAccessToken = new HashMap<>();
        refreshToAccessToken.put("accessToken", accessToken);
        //refreshToAccessToken.put("refreshToken", refreshToken);
        return refreshToAccessToken;
    }

    @Override
    public Map<String, String> generateTokens(UserResponse response) {
        return generateTokens(response.getUsername(), response.getRole(), response.getUserId());
    }
}
