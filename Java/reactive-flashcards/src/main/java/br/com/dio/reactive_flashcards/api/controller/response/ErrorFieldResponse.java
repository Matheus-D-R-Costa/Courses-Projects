package br.com.dio.reactive_flashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record ErrorFieldResponse(@JsonProperty("name")
                                 @Schema(description = "Nome do campo com erro", example = "name")
                                 String name,

                                 @JsonProperty("message")
                                 @Schema(description = "Descrição do erro", example = "o nome deve ter no máximo 255 caracteres")
                                 String message) {

    @Builder(toBuilder = true)
    public ErrorFieldResponse { }

}
