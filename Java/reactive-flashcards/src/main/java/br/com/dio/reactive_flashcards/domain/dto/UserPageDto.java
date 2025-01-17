package br.com.dio.reactive_flashcards.domain.dto;

import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public record UserPageDto(Long currentPage,
                          Long totalPages,
                          Long totalItems,
                          List<UserDocument> content) {

    public static UserPageDtoBuilder builder() {
        return new UserPageDtoBuilder();
    }

    public UserPageDtoBuilder toBuilder(final Integer LIMIT) {
        return new UserPageDtoBuilder(LIMIT, currentPage, totalItems, content);
    }

    public static UserPageDto emptyPage()  {
        return new UserPageDto(0L, 0L, 0L, List.of());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserPageDtoBuilder {

        private Integer limit;
        private Long currentPage;
        private Long totalItems;
        private List<UserDocument> content = new ArrayList<>();

        public UserPageDtoBuilder limit(final Integer LIMIT) {
            this.limit = LIMIT;
            return this;
        }

        public UserPageDtoBuilder currentPage(final Long CURRENT_PAGE) {
            this.currentPage = CURRENT_PAGE;
            return this;
        }

        public UserPageDtoBuilder totalItems(final Long TOTAL_ITEMS) {
            this.totalItems = TOTAL_ITEMS;
            return this;
        }

        public UserPageDtoBuilder content(final List<UserDocument> CONTENT) {
            this.content = CONTENT;
            return this;
        }

        public UserPageDto build() {
            var totalPages = (totalItems / limit) + ((totalItems % limit > 0) ? 1 : 0);
            return new UserPageDto(currentPage, totalPages, totalItems, content);
        }
    }
}
