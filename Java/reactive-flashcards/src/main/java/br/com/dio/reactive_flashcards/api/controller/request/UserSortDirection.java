package br.com.dio.reactive_flashcards.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public enum UserSortDirection {

    ASC(Sort.Direction.ASC),
    DESC(Sort.Direction.DESC);

    private final Sort.Direction sortBy;

}
