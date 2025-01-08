package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
public class GenericHandler extends AbstractHandleException<Exception> {

    public GenericHandler(final ObjectMapper OBJECT_MAPPER) {
        super(OBJECT_MAPPER);
    }

    @Override
    Mono<Void> handle(final ServerWebExchange EXCHANGE, final Exception EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("==== Exception:", EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }
}
