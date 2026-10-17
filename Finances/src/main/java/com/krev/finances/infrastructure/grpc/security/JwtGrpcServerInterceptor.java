package com.krev.finances.infrastructure.grpc.security;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
@GrpcGlobalServerInterceptor
public class JwtGrpcServerInterceptor implements ServerInterceptor {
    private static final Metadata.Key<String> AUTHORIZATION =
            Metadata.Key.of(
                    "authorization",
                    Metadata.ASCII_STRING_MARSHALLER
            );

    private final JwtDecoder jwtDecoder;

    public JwtGrpcServerInterceptor(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next
    ) {
        System.out.println("JWT GRPC INTERCEPTOR EXECUTADO");
        String authorization = headers.get(AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            call.close(
                    Status.UNAUTHENTICATED
                            .withDescription("Missing or invalid Authorization header"),
                    new Metadata()
            );
            return new ServerCall.Listener<>() {};
        }

        String token = authorization.substring(7);

        try {
            jwtDecoder.decode(token);
        } catch (JwtException exception) {
            call.close(
                    Status.UNAUTHENTICATED
                            .withDescription("Invalid or expired JWT"),
                    new Metadata()
            );
            return new ServerCall.Listener<>() {};
        }

        return next.startCall(call, headers);
    }
}
