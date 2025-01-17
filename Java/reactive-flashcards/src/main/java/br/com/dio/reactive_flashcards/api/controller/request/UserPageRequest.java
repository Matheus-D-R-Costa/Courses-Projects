package br.com.dio.reactive_flashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.springframework.data.domain.Sort;

import static br.com.dio.reactive_flashcards.api.controller.request.UserSortBy.NAME;
import static br.com.dio.reactive_flashcards.api.controller.request.UserSortDirection.ASC;

public record UserPageRequest(@JsonProperty("sentence")
                              String sentence,

                              @JsonProperty("page")
                              @PositiveOrZero
                              Long page,

                              @JsonProperty("limit")
                              @Min(1)
                              @Max(50)
                              Integer limit,

                              @JsonProperty("sortBy")
                              UserSortBy sortBy,

                              @JsonProperty("sortDirection")
                              UserSortDirection sortDirection) {

    @Builder(toBuilder = true)
    public UserPageRequest {
        sentence = (sentence != null ? sentence : "");
        page = (page != null ? page : 0L);
        limit = (limit != null ? limit : 10);
        sortBy = (sortBy != null ? sortBy : NAME);
        sortDirection = (sortDirection != null ? sortDirection : ASC);
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
