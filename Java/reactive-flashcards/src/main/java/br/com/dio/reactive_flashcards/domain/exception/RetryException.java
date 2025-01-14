package br.com.dio.reactive_flashcards.domain.exception;

public class RetryException extends ReactiveFlashcardsException {

    public RetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
