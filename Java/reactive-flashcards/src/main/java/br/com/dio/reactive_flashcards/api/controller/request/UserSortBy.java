package br.com.dio.reactive_flashcards.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSortBy {

    NAME("name"),
    EMAIL("email");

    private final String field;

}
