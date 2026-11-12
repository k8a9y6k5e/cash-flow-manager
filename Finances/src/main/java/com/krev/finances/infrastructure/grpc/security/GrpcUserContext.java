package com.krev.finances.infrastructure.grpc.security;

import io.grpc.Context;

public class GrpcUserContext {
    public static final Context.Key<String> USER_ID =
            Context.key("userId");
}
