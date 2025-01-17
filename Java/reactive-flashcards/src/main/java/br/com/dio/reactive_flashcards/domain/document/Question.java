package br.com.dio.reactive_flashcards.domain.document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;

public record Question(String asked,

                       @Field("asked_in")
                       OffsetDateTime askedIn,

                       String answered,

                       @Field("answered_in")
                       OffsetDateTime answeredIn,
                       String expected) {

    public Boolean isCorrect() {
        return isAnswered() && answered.equals(expected);
    }

    public Boolean isAnswered() {
        return answeredIn != null;
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
            if (isNotBlank(ASKED)) {
                this.asked = ASKED;
                this.askedIn = OffsetDateTime.now();
            }

            return this;
        }

        public QuestionBuilder answered(final String ANSWERED) {
            if (isNotBlank(ANSWERED)) {
                this.answered = ANSWERED;
                this.answeredIn = OffsetDateTime.now();
            }

            return this;
        }

        private boolean isNotBlank(CharSequence cs) {
            int strLen = cs.length();
            if (strLen == 0) return false;

            for (int i = 0; i < strLen; i++) {
                if (!Character.isWhitespace(cs.charAt(i))) return true;
            }

            return false;

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