package br.com.dio.reactive_flashcards.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record CardRestDto(@JsonProperty("ask")
                          String ask,

                          @JsonProperty("answer")
                          String answer) {

    @Builder(toBuilder = true)
    public CardRestDto { }

}
