package com.goldhub.auth_server.user.service;

import com.goldhub.auth_server.user.service.dto.VerifyTokenUserDto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.proto.jwtvalidation.JwtGrpc;
import io.grpc.proto.jwtvalidation.Token;
import io.grpc.proto.jwtvalidation.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtClient {

    private final JwtGrpc.JwtBlockingStub blockingStub;

    public JwtClient() {
        ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 50051)
            .usePlaintext()
            .build();
        blockingStub = JwtGrpc.newBlockingStub(channel);
    }

    public VerifyTokenUserDto getUserInfo(String accessToken) {
        Token token = Token.newBuilder().setToken(accessToken).build();

        try {
            UserInfo userInfo = blockingStub.getUserInfo(token);
            return VerifyTokenUserDto.of(userInfo);
        } catch (StatusRuntimeException e) {
            log.error("RPC failed: {}", e.getStatus());
            return null;
        }
    }
}
