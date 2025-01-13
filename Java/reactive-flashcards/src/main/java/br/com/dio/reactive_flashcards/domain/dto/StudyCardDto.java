package br.com.dio.reactive_flashcards.domain.dto;

import lombok.Builder;

public record StudyCardDto(String front, String back) {

    @Builder(toBuilder = true)
    public StudyCardDto { }

}
