package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import br.com.dio.reactive_flashcards.domain.exception.ReactiveFlashcardsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
public class ReactiveFlashcardsHandler extends AbstractHandleException<ReactiveFlashcardsException>{

    public ReactiveFlashcardsHandler(final ObjectMapper OBJECT_MAPPER) {
        super(OBJECT_MAPPER);
    }

    @Override
    Mono<Void> handle(final ServerWebExchange EXCHANGE, final ReactiveFlashcardsException EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("==== ReactiveFlashcardsException:", EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }

}
