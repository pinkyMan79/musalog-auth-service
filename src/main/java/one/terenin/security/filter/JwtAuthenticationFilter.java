package one.terenin.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import one.terenin.exception.common.ErrorCode;
import one.terenin.security.details.user.UserDetailsImpl;
import one.terenin.security.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String APPLICATION_JSON = "application/json";
    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON);
        // collect info from token with user details
        GrantedAuthority grantedAuthority = authResult.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new one.terenin.exception.children.AuthenticationException(ErrorCode.USER_NOT_FOUND));
        UUID userId = ((UserDetailsImpl)authResult.getPrincipal()).getResponse().getUserId();
        String username = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
        // generate tokens
        Map<String, String> tokens = jwtUtil.generateTokens(username, grantedAuthority.getAuthority(), userId);
        // write to response
        mapper.writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        return super.attemptAuthentication(request, response);
    }
}
