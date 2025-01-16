package br.com.dio.reactive_flashcards.api.controller.request;

import lombok.Builder;
import org.springframework.data.domain.Sort;

import java.util.Objects;

import static br.com.dio.reactive_flashcards.api.controller.request.UserSortBy.NAME;
import static br.com.dio.reactive_flashcards.api.controller.request.UserSortDirection.ASC;

public record UserPageRequest(String sentence,
                              Long page,
                              Integer limit,
                              UserSortBy sortBy,
                              UserSortDirection sortDirection) {

    @Builder
    public UserPageRequest {
        if (Objects.isNull(sortBy)) sortBy = NAME;
        if (Objects.isNull(sortDirection)) sortDirection = ASC;
    }

    public Long getSkip() {
        return page > 0 ? (page - 1) * limit : 0;
    }

    public Sort getSort() {
        return sortDirection.equals(UserSortDirection.DESC)
                ? Sort.by(sortBy.getField()).descending()
                : Sort.by(sortBy.getField()).ascending();
    }

}
