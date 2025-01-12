package br.com.dio.reactive_flashcards.domain.document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;
import java.util.Objects;

public record Question(String asked,

                       @Field("asked_in")
                       OffsetDateTime askedIn,

                       String answered,

                       @Field("answered_in")
                       OffsetDateTime answeredIn,
                       String expected) {

    public Boolean isAnswered() {
        return Objects.isNull(answeredIn);
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
            this.asked = ASKED;
            return this;
        }

        public QuestionBuilder askedIn(final OffsetDateTime ASKED_IN) {
            this.askedIn = ASKED_IN;
            return this;
        }
        public QuestionBuilder answered(final String ANSWERED) {
            this.answered = ANSWERED;
            return this;
        }
        public QuestionBuilder answeredIn(final OffsetDateTime ANSWERED_IN) {
            this.answeredIn = ANSWERED_IN;
            return this;
        }

        public QuestionBuilder expected(final String EXPECTED) {
            this.expected = EXPECTED;
            return this;
        }

        public Question build() {
            return new Question(asked, askedIn, answered, answeredIn, expected);
        }

    }

}