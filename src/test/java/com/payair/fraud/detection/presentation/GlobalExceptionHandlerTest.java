package com.payair.fraud.detection.presentation;

import com.payair.fraud.detection.domain.exceptions.AccountDataNotAvailable;
import com.payair.fraud.detection.domain.exceptions.DomainException;
import com.payair.fraud.detection.domain.exceptions.WrongDataFormatException;
import com.payair.fraud.detection.domain.transaction.BIN;
import com.payair.fraud.detection.infrastructure.exceptions.AuthenticationException;
import com.payair.fraud.detection.infrastructure.exceptions.HttpException;
import com.payair.fraud.detection.infrastructure.exceptions.InfrastructureException;
import com.payair.fraud.detection.infrastructure.exceptions.KeystoreException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.payair.fraud.detection.fixtures.TransactionFixtures.randomBin;
import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private static final BIN bin = randomBin();
    private static final RuntimeException exception = new RuntimeException();
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @ParameterizedTest
    @MethodSource("domainExceptions")
    public void shouldHandleDomainException(DomainException domainException, int expectedCode) {
        Response response = globalExceptionHandler.toResponse(domainException);

        assertThat(response.getStatus()).isEqualTo(expectedCode);
        assertThat(response.getEntity().toString()).contains(domainException.getMessage()); // FIXME: don't use toString(), map entity properly
    }

    @ParameterizedTest
    @MethodSource("infrastructureExceptions")
    public void shouldHandleInfrastructureException(InfrastructureException infrastructureException, int expectedCode) {
        Response response = globalExceptionHandler.toResponse(infrastructureException);

        assertThat(response.getStatus()).isEqualTo(expectedCode);
        assertThat(response.getEntity().toString()).contains(infrastructureException.getMessage());
    }

    @Test
    public void shouldHandleUnknownException() {
        Response response = globalExceptionHandler.toResponse(exception);

        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getEntity().toString()).contains("Unexpected error occurred");
    }

    private static Stream<Arguments> domainExceptions() {
        return Stream.of(
                Arguments.of(new AccountDataNotAvailable(bin), 404),
                Arguments.of(new WrongDataFormatException("Wrong format"), 400)
        );
    }

    private static Stream<Arguments> infrastructureExceptions() {
        return Stream.of(
                Arguments.of(new HttpException("HttpException", exception), 503),
                Arguments.of(new AuthenticationException("AuthenticationException", exception), 500),
                Arguments.of(new KeystoreException("AuthenticationException", exception), 500)
        );
    }
}
