package br.com.dio.reactive_flashcards.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.OffsetDateTime;
import java.util.Objects;

public record QuestionDto(String asked,
                          OffsetDateTime askedIn,
                          String answered,
                          OffsetDateTime answeredIn,
                          String expected) {

    public Boolean isCorrect() {
        return isAnswered() && answered.equals(expected);
    }

    public Boolean isAnswered() {
        return Objects.nonNull(answeredIn);
    }

    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public QuestionBuilder toBuilder() {
        return new QuestionBuilder(asked, askedIn, answered, answeredIn, expected);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionBuilder {

        private String asked;
        private OffsetDateTime askedIn;
        private String answered;
        private OffsetDateTime answeredIn;
        private String expected;

        public QuestionBuilder asked(final String ASKED) {
            if (StringUtils.isNotBlank(ASKED)) {
                this.asked = ASKED;
                this.askedIn = OffsetDateTime.now();
            }

            return this;
        }

        public QuestionBuilder answered(final String ANSWERED) {
            if (StringUtils.isNotBlank(ANSWERED)) {
                this.answered = ANSWERED;
                this.answeredIn = OffsetDateTime.now();
            }

            return this;
        }

        public QuestionBuilder expected(final String EXPECTED) {
            this.expected = EXPECTED;
            return this;
        }

        public QuestionDto build() {
            return new QuestionDto(asked, askedIn, answered, answeredIn, expected);
        }

    }

}