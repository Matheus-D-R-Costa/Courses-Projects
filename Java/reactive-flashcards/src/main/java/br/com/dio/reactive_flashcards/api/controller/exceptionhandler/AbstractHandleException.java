package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import br.com.dio.reactive_flashcards.api.controller.response.ProblemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public abstract class AbstractHandleException<T extends Exception> {

    private final ObjectMapper OBJECT_MAPPER;

    abstract Mono<Void> handle(final ServerWebExchange EXCHANGE, final T EX);

    protected void prepareExchange(final ServerWebExchange EXCHANGE, final HttpStatus STATUS_CODE) {
        EXCHANGE.getResponse().setStatusCode(STATUS_CODE);
        EXCHANGE.getResponse().getHeaders().setContentType(APPLICATION_JSON);
    }

    protected ProblemResponse buildError(final HttpStatus STATUS, final String ERROR_DESCRIPTION) {
        return ProblemResponse.builder()
                .status(STATUS.value())
                .errorDescription(ERROR_DESCRIPTION)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    protected Mono<Void> writeResponse(final ServerWebExchange EXCHANGE, final ProblemResponse PROBLEM_RESPONSE) {
        return EXCHANGE.getResponse()
                .writeWith(Mono.fromCallable(() -> new DefaultDataBufferFactory().wrap(OBJECT_MAPPER.writeValueAsBytes(PROBLEM_RESPONSE))));
    }

}
