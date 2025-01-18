package br.com.dio.reactive_flashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public record UserPageResponse(@JsonProperty("currentPage")
                               @Schema(description = "Pagina retornada", example = "1")
                               Long currentPage,

                               @JsonProperty("totalPages")
                               @Schema(description = "Total de páginas", example = "20")
                               Long totalPages,

                               @JsonProperty("totalItems")
                               @Schema(description = "Quantidade de registros paginados", example = "100")
                               Long totalItems,

                               @JsonProperty("content")
                               @Schema(description = "Dados dos usuários da página")
                               List<UserResponse> content) {

    public static UserPageResponseBuilder builder() {
        return new UserPageResponseBuilder();
    }

    public UserPageResponseBuilder toBuilder(final Integer LIMIT) {
        return new UserPageResponseBuilder(LIMIT, currentPage, totalItems, content);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserPageResponseBuilder {

        private Integer limit;
        private Long currentPage;
        private Long totalItems;
        private List<UserResponse> content = new ArrayList<>();

        public UserPageResponseBuilder limit(final Integer LIMIT) {
            this.limit = LIMIT;
            return this;
        }

        public UserPageResponseBuilder currentPage(final Long CURRENT_PAGE) {
            this.currentPage = CURRENT_PAGE;
            return this;
        }

        public UserPageResponseBuilder totalItems(final Long TOTAL_ITEMS) {
            this.totalItems = TOTAL_ITEMS;
            return this;
        }

        public UserPageResponseBuilder content(final List<UserResponse> CONTENT) {
            this.content = CONTENT;
            return this;
        }

        public UserPageResponse build() {
            var totalPages = (totalItems / limit) + ((totalItems % limit > 0) ? 1 : 0);
            return new UserPageResponse(currentPage, totalPages, totalItems, content);
        }
    }
}
