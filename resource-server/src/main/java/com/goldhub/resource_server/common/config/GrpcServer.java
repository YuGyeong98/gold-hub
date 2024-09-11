package com.goldhub.resource_server.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcServer implements ApplicationRunner {

    private final JwtServer jwtServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jwtServer.start();
        jwtServer.blockUntilShutdown();
    }
}
