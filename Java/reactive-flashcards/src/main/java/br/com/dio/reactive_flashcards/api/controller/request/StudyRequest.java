package br.com.dio.reactive_flashcards.api.controller.request;

import br.com.dio.reactive_flashcards.core.validation.MongoId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record StudyRequest(@MongoId
                           @JsonProperty("userId")
                           String userId,

                           @MongoId
                           @JsonProperty("deckId")
                           String deckId) {

    @Builder(toBuilder = true)
    public StudyRequest { }

}