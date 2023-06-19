package one.terenin.grpc.server;

import io.grpc.stub.StreamObserver;
import one.terenin.authrpc.grpc.AuthServiceGrpc;
import one.terenin.authrpc.grpc.TokenResponse;
import one.terenin.authrpc.grpc.UserRequest;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class AuthenticationService extends AuthServiceGrpc.AuthServiceImplBase {

    @Override
    public void login(UserRequest request, StreamObserver<TokenResponse> responseObserver) {
        super.login(request, responseObserver);
    }
}
