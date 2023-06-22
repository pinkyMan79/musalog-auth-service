package one.terenin.api;

import one.terenin.dto.response.TokenResponse;
import one.terenin.dto.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
@CrossOrigin(maxAge = 3600)
public interface AuthApi {

    @PostMapping("/login")
    ResponseEntity<TokenResponse> login(@RequestBody UserRequest request);

    @PutMapping("/token/update/{token}")
    ResponseEntity<TokenResponse> updateToken(@PathVariable("token") String expiredToken);

}
