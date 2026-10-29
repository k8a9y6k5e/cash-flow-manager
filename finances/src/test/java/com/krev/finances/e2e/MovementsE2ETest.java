package com.krev.finances.e2e;

import com.krev.finances.e2e.dto.AuthResponse;
import com.krev.finances.e2e.dto.LoginAuthRequest;
import com.krev.finances.e2e.dto.RegisterAuthRequest;
import com.krev.finances.infrastructure.grpc.*;
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovementsE2ETest {
    private static final String AUTH_URL = "http://localhost:8080";

    private ManagedChannel channel;
    private MovementServiceGrpc.MovementServiceBlockingStub client;

    private String token;

    private final Metadata metadata;

    public MovementsE2ETest() {
        metadata = new Metadata();
    }

    @BeforeEach
    public void setup() throws Exception {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        token = getToken();

        Metadata.Key<String> authorizationKey =
                Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

        metadata.put(authorizationKey, "Bearer " + token);

        client = MovementServiceGrpc.newBlockingStub(channel);
    }

    private String getToken() throws Exception {
        AuthResponse response = registerUser();

        if(response != null){
            return response.token();
        }
        else{
            response = loginUser();

            return response.token();
        }
    }

    private AuthResponse registerUser() throws Exception {
        try{
            RestClient restClient = RestClient.create();

            AuthResponse response = restClient.post()
                    .uri(AUTH_URL + "/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(
                            new RegisterAuthRequest(
                                    "test@example.com",
                                    "test",
                                    "test1234"
                            )
                    )
                    .retrieve()
                    .body(AuthResponse.class);

            return response;
        }
        catch(HttpClientErrorException ex) {
            HttpStatusCode status = ex.getStatusCode();

            if(status == HttpStatus.UNAUTHORIZED){
                return null;
            }
        }
        throw new Exception("Register method Failed");
    }

    private AuthResponse loginUser() throws Exception {
        try{
            RestClient restClient = RestClient.create();

            AuthResponse response = restClient.post()
                    .uri(AUTH_URL + "/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(
                            new LoginAuthRequest(
                                    "test@example.com",
                                    "test",
                                    "test1234"
                            )
                    )
                    .retrieve()
                    .body(AuthResponse.class);

            return response;
        }
        catch(HttpClientErrorException ex) {
            HttpStatusCode status = ex.getStatusCode();

            if(status == HttpStatus.UNAUTHORIZED){
                return null;
            }
        }
        throw new Exception("Login method Failed");
    }

    @AfterEach
    public void teardown() throws InterruptedException {
        channel.shutdown();
        channel.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void shouldReturnUnauthorized() {
        CreateMovementRequest createRequest = CreateMovementRequest
                .newBuilder()
                .setType(MovementType.EXPENSE)
                .setAmount(10)
                .build();

        StatusRuntimeException createException = assertThrows(
                StatusRuntimeException.class,
                () -> client.create(createRequest)
        );

        assertEquals(
                Status.Code.UNAUTHENTICATED,
                createException.getStatus().getCode()
        );

        GetMovementsByMonthRequest getRequest = GetMovementsByMonthRequest
                .newBuilder()
                .setMonth(1)
                .setYear(2010)
                .build();

        StatusRuntimeException getException = assertThrows(
                StatusRuntimeException.class,
                () -> client.getMovementsByMonth(getRequest)
        );

        assertEquals(
                Status.Code.UNAUTHENTICATED,
                getException.getStatus().getCode()
        );
    }

    @Test
    public void shouldCreateMovement() {
        CreateMovementRequest request = CreateMovementRequest
                .newBuilder()
                .setType(MovementType.EXPENSE)
                .setAmount(10)
                .build();

        CreateMovementResponse response = client
                .withInterceptors(
                        MetadataUtils.newAttachHeadersInterceptor(metadata)
                )
                .create(request);

        assertNotNull(response);
        assertTrue(response.getId() > 0);
        assertEquals(MovementType.EXPENSE, response.getType());
        assertEquals(10, response.getAmount());
    }

    @Test
    public void shouldGetMovementsFilteredByMonth() {
        CreateMovementRequest createRequest = CreateMovementRequest
                .newBuilder()
                .setType(MovementType.EXPENSE)
                .setAmount(10)
                .build();

        CreateMovementResponse created = client
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata))
                .create(createRequest);

        java.time.LocalDate today = java.time.LocalDate.now();

        GetMovementsByMonthRequest request = GetMovementsByMonthRequest
                .newBuilder()
                .setMonth(today.getMonthValue())
                .setYear(today.getYear())
                .build();

        GetMovementsByMonthResponse response = client
                .withInterceptors(
                        MetadataUtils.newAttachHeadersInterceptor(metadata)
                )
                .getMovementsByMonth(request);

        assertNotNull(response);
        assertTrue(response.getMovementsList().stream()
                .anyMatch(movement -> movement.getId() == created.getId()));
    }
}
