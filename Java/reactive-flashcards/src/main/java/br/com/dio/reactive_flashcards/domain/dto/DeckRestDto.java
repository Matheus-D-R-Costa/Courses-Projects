package br.com.dio.reactive_flashcards.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public record DeckRestDto(@JsonProperty("name")
                          String name,

                          @JsonProperty("info")
                          String info,

                          @JsonProperty("author")
                          String author,

                          @JsonProperty("cards")
                          List<CardRestDto> cards) {

    @Builder(toBuilder = true)
    public DeckRestDto { }

}
