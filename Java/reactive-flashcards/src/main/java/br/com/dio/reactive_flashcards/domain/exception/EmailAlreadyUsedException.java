package br.com.dio.reactive_flashcards.domain.exception;

public class EmailAlreadyUsedException extends ReactiveFlashcardsException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
