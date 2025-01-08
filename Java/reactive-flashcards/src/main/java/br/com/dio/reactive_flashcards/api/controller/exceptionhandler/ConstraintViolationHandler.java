package br.com.dio.reactive_flashcards.api.controller.exceptionhandler;

import br.com.dio.reactive_flashcards.api.controller.response.ErrorFieldResponse;
import br.com.dio.reactive_flashcards.api.controller.response.ProblemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public class ConstraintViolationHandler extends AbstractHandleException<ConstraintViolationException> {

    public ConstraintViolationHandler(final ObjectMapper OBJECT_MAPPER) {
        super(OBJECT_MAPPER);
    }

    @Override
    Mono<Void> handle(final ServerWebExchange EXCHANGE, final ConstraintViolationException EX) {
        return Mono.fromCallable(() -> {
                    prepareExchange(EXCHANGE, BAD_REQUEST);
                    return GENERIC_BAD_REQUEST.getMessage();
                }).map(message -> buildError(BAD_REQUEST, message))
                .flatMap(response -> buildRequestErrorMessage(response, EX))
                .doFirst(() -> log.error("==== ConstraintViolationException:", EX))
                .flatMap(problemResponse -> writeResponse(EXCHANGE, problemResponse));
    }

    private Mono<ProblemResponse> buildRequestErrorMessage(final ProblemResponse RESPONSE, final ConstraintViolationException EX) {
        return Flux.fromIterable(EX.getConstraintViolations())
                .map(constraintViolation -> ErrorFieldResponse.builder()
                        .name(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().toString())
                        .message(constraintViolation.getMessage()).build())
                .collectList()
                .map(problems -> RESPONSE.toBuilder().fields(problems).build());
    }

}
