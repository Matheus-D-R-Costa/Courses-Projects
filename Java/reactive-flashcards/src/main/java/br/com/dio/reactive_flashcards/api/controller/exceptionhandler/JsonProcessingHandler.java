package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_METHOD_NOT_ALLOW;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@Slf4j
public class JsonProcessingHandler extends AbstractHandleException<JsonProcessingException> {

    public JsonProcessingHandler(final ObjectMapper OBJECT_MAPPER) {
        super(OBJECT_MAPPER);
    }

    @Override
    Mono<Void> handle(final ServerWebExchange EXCHANGE, final JsonProcessingException EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, METHOD_NOT_ALLOWED);
                    return GENERIC_METHOD_NOT_ALLOW.getMessage();
                }).map(message -> buildError(METHOD_NOT_ALLOWED, message))
                .doFirst(() -> log.error("==== JsonProcessingException: Failed to map exception for the request {}",
                        EXCHANGE.getRequest().getPath().value(), EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }
}
