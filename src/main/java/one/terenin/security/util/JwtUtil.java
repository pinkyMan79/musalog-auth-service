package one.terenin.security.util;

import one.terenin.authrpc.grpc.UserResponse;
import org.hibernate.boot.registry.selector.StrategyRegistration;

import java.util.Map;
import java.util.UUID;

public interface JwtUtil {

    Map<String, String> generateTokens(String subject, String authority, UUID userId);

    Map<String, String> generateTokens(UserResponse response);

}
