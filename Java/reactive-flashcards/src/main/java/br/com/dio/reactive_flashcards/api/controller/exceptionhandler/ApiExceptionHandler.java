package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import br.com.dio.reactive_flashcards.domain.exception.ReactiveFlashcardsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
@Slf4j
@AllArgsConstructor
public class ApiExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper OBJECT_MAPPER;
    private final MessageSource MESSAGE_SOURCE;

    @Override
    public Mono<Void> handle(final ServerWebExchange EXCHANGE, Throwable EX) {
        return Mono.error(EX)
                .onErrorResume(MethodNotAllowedException.class, e -> new MethodNotAllowHandler(OBJECT_MAPPER).handle(EXCHANGE, e))
                .onErrorResume(NotFoundException.class, e -> new NotFoundHandler(OBJECT_MAPPER).handle(EXCHANGE, e))
                .onErrorResume(ConstraintViolationException.class, e -> new ConstraintViolationHandler(OBJECT_MAPPER).handle(EXCHANGE, e))
                .onErrorResume(WebExchangeBindException.class, e -> new WebExchangeBindHandler(OBJECT_MAPPER, MESSAGE_SOURCE).handle(EXCHANGE, e))
                .onErrorResume(ResponseStatusException.class, e -> new ResponseStatusHandler(OBJECT_MAPPER).handle(EXCHANGE, e))
                .onErrorResume(ReactiveFlashcardsException.class, e -> new ReactiveFlashcardsHandler(OBJECT_MAPPER).handle(EXCHANGE, e))
                .onErrorResume(Exception.class, e -> new GenericHandler(OBJECT_MAPPER).handle(EXCHANGE, e))
                .onErrorResume(JsonProcessingException.class, e -> new JsonProcessingHandler(OBJECT_MAPPER).handle(EXCHANGE, e))
                .then();
    }

}