package br.com.dio.reactive_flashcards.domain.dto;

import org.apache.commons.collections4.CollectionUtils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public record StudyDto(String id,
                       String userId,
                       StudyDeckDto studyDeck,
                       List<QuestionDto> questions,
                       List<String> remainAsks,
                       Boolean completed,
                       OffsetDateTime createdAt,
                       OffsetDateTime updatedAt) {

    public static StudyDtoBuilder builder() {
        return new StudyDtoBuilder();
    }

    public StudyDtoBuilder toBuilder() {
        return new StudyDtoBuilder(id, userId, studyDeck, questions, remainAsks, createdAt, updatedAt);
    }

    public Boolean hasAnyAnswer() {
        return CollectionUtils.isNotEmpty(remainAsks);
    }

    public static class StudyDtoBuilder {

        private String id;
        private String userId;
        private StudyDeckDto studyDeck;
        private List<QuestionDto> questions = new ArrayList<>();
        private List<String> remainAsks = new ArrayList<>();
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public StudyDtoBuilder() { }

        public StudyDtoBuilder(
                String id,
                String userId,
                StudyDeckDto studyDeck,
                List<QuestionDto> questions,
                List<String> remainAsks,
                OffsetDateTime createdAt,
                OffsetDateTime updatedAt
        ) {
            this.id = id;
            this.userId = userId;
            this.studyDeck = studyDeck;
            this.questions = questions;
            this.remainAsks = remainAsks;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public StudyDtoBuilder id(final String ID) {
            this.id = ID;
            return this;
        }

        public StudyDtoBuilder userId(final String USER_ID) {
            this.userId = USER_ID;
            return this;
        }

        public StudyDtoBuilder studyDeck(final StudyDeckDto STUDY_DECK) {
            this.studyDeck = STUDY_DECK;
            return this;
        }

        public StudyDtoBuilder questions(final List<QuestionDto> QUESTIONS) {
            this.questions = QUESTIONS;
            return this;
        }

        public StudyDtoBuilder question(final QuestionDto QUESTION) {
            this.questions.add(QUESTION);
            return this;
        }

        public StudyDtoBuilder remainAsks(final List<String> REMAIN_ASKS) {
            this.remainAsks = REMAIN_ASKS;
            return this;
        }

        public StudyDtoBuilder createdAt(final OffsetDateTime CREATED_AT) {
            this.createdAt = CREATED_AT;
            return this;
        }

        public StudyDtoBuilder updatedAt(final OffsetDateTime UPDATED_AT) {
            this.updatedAt = UPDATED_AT;
            return this;
        }

        public StudyDto build() {
            var rightQuestions = questions.stream()
                    .filter(QuestionDto::isCorrect)
                    .toList();
            var completed = rightQuestions.size() == studyDeck.cards().size();

            return new StudyDto(id, userId, studyDeck, questions, remainAsks, completed, createdAt, updatedAt);
        }

    }

}