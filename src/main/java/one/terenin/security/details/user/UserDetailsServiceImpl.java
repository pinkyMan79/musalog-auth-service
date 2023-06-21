/*
package one.terenin.security.details.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.terenin.dto.response.UserResponse;
import one.terenin.exception.children.ServiceCallException;
import one.terenin.exception.children.ServiceNotFoundException;
import one.terenin.exception.common.ErrorCode;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RestTemplate restTemplate;
    private final DiscoveryClient client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        URI serviceURI = client.getInstances("musalog-user-service").stream()
                .map(ServiceInstance::getUri)
                .findFirst()
                .map(si -> si.resolve("/api/v1/user/login/by/" + username))
                .orElseThrow(() -> new ServiceNotFoundException(ErrorCode.SERVICE_NOT_FOUND));
        ResponseEntity<UserResponse> response = restTemplate.getForEntity(serviceURI, UserResponse.class);
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()){
            throw new ServiceCallException(ErrorCode.SERVICE_CALL_REJECTED);
        }
        log.info("{}{}", "INFO: find user entity with login: ", response.toString());
        return new UserDetailsImpl(response.getBody());
    }
}

*/
