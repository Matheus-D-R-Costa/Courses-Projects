package br.com.dio.reactive_flashcards.domain.document;

import lombok.Builder;

public record StudyCard(String front, String back) {

    @Builder(toBuilder = true)
    public StudyCard {}

}
