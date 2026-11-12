package com.krev.finances.infrastructure.grpc.security;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.security.oauth2.jwt.Jwt;
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
            Jwt jwtToken = jwtDecoder.decode(token);

            String userId = jwtToken.getSubject();

            Context context = Context.current()
                    .withValue(GrpcUserContext.USER_ID, userId);

            return Contexts.interceptCall(context, call, headers, next);
        } catch (JwtException exception) {
            call.close(
                    Status.UNAUTHENTICATED
                            .withDescription("Invalid or expired JWT"),
                    new Metadata()
            );
            return new ServerCall.Listener<>() {};
        }
    }
}
