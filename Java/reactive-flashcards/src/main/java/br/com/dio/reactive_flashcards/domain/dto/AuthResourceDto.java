package br.com.dio.reactive_flashcards.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record AuthResourceDto(@JsonProperty("token")
                              String token,

                              @JsonProperty("expiresIn")
                              Long expiresIn) {

    @Builder(toBuilder = true)
    public AuthResourceDto { }

}
