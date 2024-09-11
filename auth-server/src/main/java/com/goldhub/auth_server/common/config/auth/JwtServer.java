package com.goldhub.auth_server.common.config.auth;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.proto.jwtvalidation.JwtGrpc;
import io.grpc.proto.jwtvalidation.Token;
import io.grpc.proto.jwtvalidation.User;
import io.grpc.proto.jwtvalidation.UserInfo;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtServer extends JwtGrpc.JwtImplBase {

    private final JwtUtil jwtUtil;
    private Server server;

    public void start() throws IOException {
        int port = 50051;

        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
            .addService(new JwtServer(jwtUtil))
            .build()
            .start();

        log.info("Server started, listening on {}", port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("*** shutting down gRPC server since JVM is shutting down");
            try {
                JwtServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            log.info("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    @Override
    public void getUserInfo(Token request, StreamObserver<UserInfo> responseObserver) {
        String token = request.getToken();

        if (jwtUtil.verifyToken(token)) {
            responseObserver.onNext(getSuccessUserInfo(token));
        } else {
            responseObserver.onNext(getFailureUserInfo());
        }

        responseObserver.onCompleted();
    }

    private UserInfo getSuccessUserInfo(String token) {
        Long userId = jwtUtil.getUserId(token);
        String userName = jwtUtil.getUsername(token);

        User user = User.newBuilder()
            .setUserId(String.valueOf(userId))
            .setUsername(userName)
            .build();

        return UserInfo.newBuilder()
            .setIsValid(true)
            .setUser(user)
            .build();
    }

    private UserInfo getFailureUserInfo() {
        return UserInfo.newBuilder()
            .setIsValid(false)
            .build();
    }
}
