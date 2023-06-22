package one.terenin.grpc.surityinterceptor;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import one.terenin.security.propertysource.JWTPropertySource;
import org.springframework.stereotype.Component;

/** @apiNote
 * That interceptor is not be presented in here because this
 * service need only for generate tokens and update it.
 * So, in simplify I gonna to call UserService get the response from it and generate token
 * base on {@link one.terenin.authrpc.grpc.UserResponse} and return it to API GW,
 * parse the http2.0 to the http1.1 and move it to client
 */

@Component
public class JwtAuthInterceptor implements ServerInterceptor {

    private final JWTPropertySource propertySource;
    private final static String TOKEN_PREFIX = "Bearer ";
    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization",
            Metadata.ASCII_STRING_MARSHALLER);
    // get it from user-entity or user response
    public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("clientId");

    public JwtAuthInterceptor(JWTPropertySource propertySource) {
        this.propertySource = propertySource;
    }


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        String exceptedToken = headers.get(AUTHORIZATION_METADATA_KEY);
        Status status;
        if (exceptedToken == null){
            status = Status.UNAUTHENTICATED.withDescription("Token is missing");
        }else if (!exceptedToken.startsWith(TOKEN_PREFIX)){
            status = Status.ABORTED.withDescription("Unknown token prefix type");
        }else {
            try {
                String token = exceptedToken.substring(TOKEN_PREFIX.length()).trim();
                JwtParser parser = Jwts.parser()
                        .setSigningKey(propertySource.getJwtSecret());
                Jws<Claims> claims = parser.parseClaimsJws(token);
                Context context = Context.current().withValue(CLIENT_ID_CONTEXT_KEY, claims.getBody().getSubject());
                // add status here?
                return Contexts.interceptCall(context, call, headers, next);
            } catch (Exception e) {
                status = Status.UNAUTHENTICATED.withDescription("Throw exception through intercept auth");
            }

        }
        return new ServerCall.Listener<ReqT>() {
            @Override
            public void onMessage(ReqT message) {
                super.onMessage(message);
            }
        };
    }

    public JWTPropertySource getPropertySource() {
        return propertySource;
    }
}
