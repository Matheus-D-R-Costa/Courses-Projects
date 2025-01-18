package br.com.dio.reactive_flashcards.api.controller.request;

import br.com.dio.reactive_flashcards.core.validation.MongoId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record StudyRequest(@MongoId
                           @JsonProperty("userId")
                           @Schema(description = "Identificador do usuário", example = "66342b8418c87a1a8a8ffcb0")
                           String userId,

                           @MongoId
                           @JsonProperty("deckId")
                           @Schema(description = "Identificador do deck", example = "6633f853d783b72353b83013")
                           String deckId) {

    @Builder(toBuilder = true)
    public StudyRequest { }

}