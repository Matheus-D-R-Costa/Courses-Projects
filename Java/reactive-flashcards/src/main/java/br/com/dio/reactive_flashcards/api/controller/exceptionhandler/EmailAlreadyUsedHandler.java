package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import br.com.dio.reactive_flashcards.domain.exception.EmailAlreadyUsedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public class EmailAlreadyUsedHandler extends AbstractHandleException<EmailAlreadyUsedException> {


    public EmailAlreadyUsedHandler(ObjectMapper OBJECT_MAPPER) {
        super(OBJECT_MAPPER);
    }

    @Override
    Mono<Void> handle(ServerWebExchange EXCHANGE, EmailAlreadyUsedException EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, BAD_REQUEST);
                    return EX.getMessage();
                }).map(message -> buildError(BAD_REQUEST, message))
                .doFirst(() -> log.error("==== emailAlreadyUsedException:", EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }



}
