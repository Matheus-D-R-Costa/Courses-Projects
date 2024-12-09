package br.com.dio.reactive_flashcards.domain.exception;

public class NotFoundException extends ReactiveFlashcardsException {

    public NotFoundException(String message) {
        super(message);
    }

}
