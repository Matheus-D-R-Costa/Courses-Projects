package br.com.dio.reactive_flashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record UserRequest(@NotBlank
                          @Size(min = 1, max = 255)
                          @JsonProperty("name")
                          @Schema(description = "Nome do usuário", example = "João Empreendedor")
                          String name,

                          @NotBlank
                          @Size(min = 1, max = 255)
                          @Email
                          @JsonProperty("email")
                          @Schema(description = "E-mail do usuário", example = "joao@empresa.com.br")
                          String email) {

    @Builder(toBuilder = true)
    public UserRequest {}

}
