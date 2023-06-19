package one.terenin.service;

import one.terenin.dto.request.UserRequest;
import one.terenin.dto.response.TokenResponse;

public interface AuthService {

    TokenResponse login(UserRequest request);

}
