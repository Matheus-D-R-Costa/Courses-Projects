package br.com.dio.reactive_flashcards.domain.helper;

import br.com.dio.reactive_flashcards.core.RetryConfig;
import br.com.dio.reactive_flashcards.domain.exception.RetryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.util.retry.Retry;

import java.util.function.Predicate;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_MAX_RETRIES;

@AllArgsConstructor
@Component
@Slf4j
public class RetryHelper {

    private final RetryConfig CONFIG;

    public Retry processRetry(final String RETRY_IDENTIFIER, final Predicate<? super Throwable> ERROR_FILTER) {
        return Retry.backoff(CONFIG.maxRetries(), CONFIG.minDurationSeconds())
                .filter(ERROR_FILTER)
                .doBeforeRetry(retrySignal -> log.warn("==== Retrying {} - {} - times(s)",
                        RETRY_IDENTIFIER, retrySignal))
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) ->
                        new RetryException(GENERIC_MAX_RETRIES.getMessage(), retrySignal.failure())));
    }

}
