package one.terenin.controller;

import lombok.RequiredArgsConstructor;
import one.terenin.api.AuthApi;
import one.terenin.dto.request.UserRequest;
import one.terenin.dto.response.TokenResponse;
import one.terenin.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService service;

    @Override
    public ResponseEntity<TokenResponse> login(UserRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @Override
    public ResponseEntity<TokenResponse> updateToken(String expiredToken) {
        return ResponseEntity.ok(service.updateToken(expiredToken));
    }
}
