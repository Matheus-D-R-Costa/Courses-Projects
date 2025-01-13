package br.com.dio.reactive_flashcards.domain.dto;

import lombok.Builder;
import java.util.Set;

public record StudyDeckDto(String deckId, Set<StudyCardDto> cards) {

    @Builder(toBuilder = true)
    public StudyDeckDto { }

}