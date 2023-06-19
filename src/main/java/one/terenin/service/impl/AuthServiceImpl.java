package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.request.UserRequest;
import one.terenin.dto.response.TokenResponse;
import one.terenin.dto.response.UserResponse;
import one.terenin.exception.children.ServiceNotFoundException;
import one.terenin.exception.common.ErrorCode;
import one.terenin.mapper.TokenMapper;
import one.terenin.repository.TokenRepository;
import one.terenin.security.util.JwtUtil;
import one.terenin.service.AuthService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenRepository repository;
    private final DiscoveryClient client;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final TokenMapper mapper;

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
        ResponseEntity<UserResponse> forEntity = restTemplate
                .getForEntity(serviceHost + ":" + String.valueOf(servicePort), UserResponse.class);
        UserResponse response = forEntity.getBody();
        assert response != null;
        TokenResponse accessToken = TokenResponse.builder()
                .token(jwtUtil.generateTokens(response).get("accessToken"))
                .role(response.getRole())
                .userId(response.getUserId())
                .build();
        repository.save(mapper.map(accessToken));
        return accessToken;
    }
}
