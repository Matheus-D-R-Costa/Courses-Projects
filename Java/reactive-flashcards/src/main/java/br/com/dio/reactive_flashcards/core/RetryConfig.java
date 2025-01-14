package br.com.dio.reactive_flashcards.core;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("retry-config")
public record RetryConfig(Long maxRetries,
                          Long minDuration) {

    public Duration minDurationSeconds() {
        return Duration.ofSeconds(minDuration);
    }

}
