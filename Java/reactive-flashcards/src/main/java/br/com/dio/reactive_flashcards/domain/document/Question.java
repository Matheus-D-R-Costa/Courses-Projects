package br.com.dio.reactive_flashcards.domain.document;

public record Question(String asked,
                       String answered,
                       String expected) {
}
