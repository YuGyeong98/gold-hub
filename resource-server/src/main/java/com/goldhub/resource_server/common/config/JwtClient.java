package com.goldhub.resource_server.common.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.proto.jwtvalidation.JwtGrpc;
import io.grpc.proto.jwtvalidation.Token;
import io.grpc.proto.jwtvalidation.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtClient {

    private final JwtGrpc.JwtBlockingStub blockingStub;

    public JwtClient() {
        ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 50051)
            .usePlaintext()
            .build();
        blockingStub = JwtGrpc.newBlockingStub(channel);
    }

    public UserInfo getUserInfo(String accessToken) {
        Token token = Token.newBuilder().setToken(accessToken).build();

        try {
            return blockingStub.getUserInfo(token);
        } catch (StatusRuntimeException e) {
            log.error("RPC failed: {}", e.getStatus());
            return null;
        }
    }
}
