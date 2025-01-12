package br.com.dio.reactive_flashcards.domain.document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record StudyCard(String front, String back) {

    public static StudyCardBuilder builder() {
        return new StudyCardBuilder();
    }

    public StudyCardBuilder toBuilder() {
        return new StudyCardBuilder(front, back);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudyCardBuilder {

        private String front;
        private  String back;

        public StudyCardBuilder front(final String FRONT) {
            this.front = FRONT;
            return this;
        }

        public StudyCardBuilder back(final String BACK) {
            this.back = BACK;
            return this;
        }

        public StudyCard build() {
            return new StudyCard(front, back);
        }

    }

}
