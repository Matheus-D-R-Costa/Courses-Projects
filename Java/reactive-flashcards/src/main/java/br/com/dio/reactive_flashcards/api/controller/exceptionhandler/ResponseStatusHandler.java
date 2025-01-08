package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class ResponseStatusHandler extends AbstractHandleException<ResponseStatusException> {

    public ResponseStatusHandler(final ObjectMapper OBJECT_MAPPER) {
        super(OBJECT_MAPPER);
    }

    @Override
    Mono<Void> handle(final ServerWebExchange EXCHANGE, final ResponseStatusException EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, NOT_FOUND);
                    return GENERIC_NOT_FOUND.getMessage();
                }).map(message -> buildError(NOT_FOUND, message))
                .doFirst(() -> log.error("==== ResponseStatusException:", EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }

}
