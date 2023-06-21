package one.terenin.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.terenin.dto.request.UserRequest;
import one.terenin.dto.response.TokenResponse;
import one.terenin.dto.response.UserResponse;
import one.terenin.entity.TokenEntity;
import one.terenin.exception.children.ServiceCallException;
import one.terenin.exception.children.ServiceNotFoundException;
import one.terenin.exception.children.TokenException;
import one.terenin.exception.common.ErrorCode;
import one.terenin.mapper.TokenMapper;
import one.terenin.repository.TokenRepository;
import one.terenin.security.propertysource.JWTPropertySource;
import one.terenin.security.util.JwtUtil;
import one.terenin.service.AuthService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenRepository repository;
    private final DiscoveryClient client;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final TokenMapper mapper;
    private final JWTPropertySource propertySource;

    @Override
    public TokenResponse login(UserRequest request) {
        String serviceHost = client.getInstances("musalog-user-service").stream()
                .findFirst()
                .map(ServiceInstance::getHost)
                .orElseThrow(() -> new ServiceNotFoundException(ErrorCode.SERVICE_NOT_FOUND));
        Integer servicePort = client.getInstances("musalog-user-service").stream()
                .findFirst()
                .map(ServiceInstance::getPort)
                .orElseThrow(() -> new ServiceNotFoundException(ErrorCode.SERVICE_NOT_FOUND));
        URI serviceURI = client.getInstances("musalog-user-service").stream()
                .map(ServiceInstance::getUri)
                .findFirst()
                .map(si -> si.resolve("/api/v1/user/login"))
                .orElseThrow(() -> new ServiceNotFoundException(ErrorCode.SERVICE_NOT_FOUND));
        ResponseEntity<UserResponse> forEntity = restTemplate
                .postForEntity(serviceURI, request, UserResponse.class);
        UserResponse response = forEntity.getBody();
        log.info("{}{}", serviceURI, "Founded URI");
        System.out.println(serviceURI + " Founded URI");
        if (response == null){
            throw new ServiceCallException(ErrorCode.SERVICE_CALL_REJECTED);
        }
        TokenResponse accessToken = TokenResponse.builder()
                .token(jwtUtil.generateTokens(response).get("accessToken"))
                .role(response.getRole())
                .userId(response.getUserId())
                .username(response.getUsername())
                .build();
        repository.save(mapper.map(accessToken));
        return accessToken;
    }

    @Override
    public TokenResponse updateToken(String expiredToken) {

        TokenEntity tokenEntity = repository.findTokenEntityByToken(expiredToken)
                .orElseThrow(() -> new TokenException(ErrorCode.TOKEN_GENERATION_REJECTED));
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(propertySource.getJwtSecret().getBytes()))
                    .parseClaimsJws(expiredToken)
                    .getBody();
            String subject = body.get("subject", String.class);
            String authority = body.get("authority", String.class);
            UUID userId = body.get("userId", UUID.class);
            return TokenResponse.builder()
                    .token(jwtUtil.generateTokens(subject, authority, userId).get("accessToken"))
                    .role(authority)
                    .userId(userId)
                    .username(subject)
                    .build();
        } catch(ExpiredJwtException e){
            //System.out.println("token expired for id : " + e.getClaims().getId());
            Claims claims = e.getClaims();
            String subject = claims.get("subject", String.class);
            String authority = claims.get("authority", String.class);
            UUID userId = UUID.fromString(claims.get("userId", String.class));
            return TokenResponse.builder()
                    .token(jwtUtil.generateTokens(subject, authority, userId).get("accessToken"))
                    .role(authority)
                    .userId(userId)
                    .username(subject)
                    .build();
        }
    }
}
