package br.com.dio.reactive_flashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record CardRequest(@NotBlank
                          @Size(min = 1, max = 255)
                          @JsonProperty("front")
                          @Schema(description = "Pergunta do card", example = "blue")
                          String front,

                          @NotBlank
                          @Size(min = 1, max = 255)
                          @JsonProperty("back")
                          @Schema(description = "Resposta do card", example = "azul")
                          String back) {

    @Builder(toBuilder = true)
    public CardRequest {}

}
