package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import br.com.dio.reactive_flashcards.domain.exception.DeckPendingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
public class DeckPendingHandler extends AbstractHandleException<DeckPendingException> {

    public DeckPendingHandler(ObjectMapper OBJECT_MAPPER) {
        super(OBJECT_MAPPER);
    }

    @Override
    Mono<Void> handle(ServerWebExchange EXCHANGE, DeckPendingException EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, CONFLICT);
                    return EX.getMessage();
                }).map(message -> buildError(CONFLICT, message))
                .doFirst(() -> log.error("==== DeckPendingException:", EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }

}
