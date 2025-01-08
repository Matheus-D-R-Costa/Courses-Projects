package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import br.com.dio.reactive_flashcards.api.controller.response.ErrorFieldResponse;
import br.com.dio.reactive_flashcards.api.controller.response.ProblemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public class WebExchangeBindHandler extends AbstractHandleException<WebExchangeBindException>{

    private final MessageSource MESSAGE_SOURCE;

    public WebExchangeBindHandler(final ObjectMapper OBJECT_MAPPER, final MessageSource MESSAGE_SOURCE) {
        super(OBJECT_MAPPER);
        this.MESSAGE_SOURCE = MESSAGE_SOURCE;
    }

    @Override
    Mono<Void> handle(final ServerWebExchange EXCHANGE, WebExchangeBindException EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, BAD_REQUEST);
                    return GENERIC_BAD_REQUEST.getMessage();
                }).map(message -> buildError(BAD_REQUEST, message))
                .flatMap(response -> buildRequestErrorMessage(response, EX))
                .doFirst(() -> log.error("==== WebExchangeBindException:", EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }

    private Mono<ProblemResponse> buildRequestErrorMessage(final ProblemResponse RESPONSE, final WebExchangeBindException EX) {
        return Flux.fromIterable(EX.getAllErrors())
                .map(objectError -> ErrorFieldResponse.builder()
                        .name(objectError instanceof FieldError fieldError ? fieldError.getField(): objectError.getObjectName())
                        .message(MESSAGE_SOURCE.getMessage(objectError, LocaleContextHolder.getLocale()))
                        .build())
                .collectList()
                .map(problems -> RESPONSE.toBuilder().fields(problems).build());
    }

}
