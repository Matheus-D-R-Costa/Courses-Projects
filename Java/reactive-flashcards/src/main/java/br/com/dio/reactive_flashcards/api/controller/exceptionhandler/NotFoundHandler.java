package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class NotFoundHandler extends AbstractHandleException<NotFoundException> {

    public NotFoundHandler(final ObjectMapper OBJECT_MAPPER) {
        super(OBJECT_MAPPER);
    }

    @Override
    Mono<Void> handle(final ServerWebExchange EXCHANGE, final NotFoundException EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, NOT_FOUND);
                    return EX.getMessage();
                }).map(message -> buildError(NOT_FOUND, message))
                .doFirst(() -> log.error("==== NotFoundException:", EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }
}
