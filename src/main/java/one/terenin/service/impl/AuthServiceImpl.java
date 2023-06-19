package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.request.UserRequest;
import one.terenin.dto.response.TokenResponse;
import one.terenin.repository.TokenRepository;
import one.terenin.service.AuthService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenRepository repository;

    @Override
    public TokenResponse login(UserRequest request) {
        return null;
    }
}
