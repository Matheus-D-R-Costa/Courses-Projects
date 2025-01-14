package br.com.dio.reactive_flashcards.core;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("retry-config")
@ConstructorBinding
public record RetryConfig(Long maxRetries,
                          Long minDuration) {

}
