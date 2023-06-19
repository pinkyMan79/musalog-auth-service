package one.terenin.api;

import one.terenin.dto.response.TokenResponse;
import one.terenin.dto.request.UserRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthApi {

    @PostMapping("/login")
    TokenResponse login(@RequestBody UserRequest request);

    @PutMapping("/token/update/{token}")
    TokenResponse updateToken(@PathVariable("token") String expiredToken);

}
