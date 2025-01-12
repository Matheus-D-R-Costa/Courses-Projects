package br.com.dio.reactive_flashcards.domain.document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "studies")
public record StudyDocument(@Id
                            String id,

                            @Field("user_id")
                            String userId,

                            @Field("is_complete")
                            Boolean isComplete,

                            @Field("study_deck")
                            StudyDeck studyDeck,

                            List<Question> questions,

                            @CreatedDate
                            @Field("created_at")
                            OffsetDateTime createdAt,

                            @LastModifiedDate
                            @Field("updated_at")
                            OffsetDateTime updatedAt) {

    public static StudyDocumentBuilder builder() {
        return new StudyDocumentBuilder();
    }

    public StudyDocumentBuilder toBuilder() {
        return new StudyDocumentBuilder(id, userId, isComplete, studyDeck, questions, createdAt, updatedAt);
    }

    public Question getLastPendingQuestion() {
        return questions.stream()
                .filter(q -> Objects.isNull(q.answeredIn())).findFirst().orElseThrow();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudyDocumentBuilder {

        private String id;
        private String userId;
        private Boolean isComplete = false;
        private StudyDeck studyDeck;
        private List<Question> questions  = new ArrayList<>();
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public StudyDocumentBuilder id(final String ID) {
            this.id = ID;
            return this;
        }

        public StudyDocumentBuilder userId(final String USER_ID) {
            this.userId = USER_ID;
            return this;
        }

        public StudyDocumentBuilder isComplete(final Boolean IS_COMPLETE) {
            this.isComplete = IS_COMPLETE;
            return this;
        }

        public StudyDocumentBuilder studyDeck(final StudyDeck STUDY_DECK) {
            this.studyDeck = STUDY_DECK;
            return this;
        }

        public StudyDocumentBuilder questions(final List<Question> QUESTIONS) {
            this.questions = QUESTIONS;
            return this;
        }

        public StudyDocumentBuilder question(final Question QUESTION) {
            this.questions.add(QUESTION);
            return this;
        }

        public StudyDocumentBuilder createdAt(final OffsetDateTime CREATED_AT) {
            this.createdAt = CREATED_AT;
            return this;
        }

        public StudyDocumentBuilder updatedAt(final OffsetDateTime UPDATED_AT) {
            this.updatedAt = UPDATED_AT;
            return this;
        }

        public StudyDocument build() {
            return new StudyDocument(id, userId, isComplete, studyDeck,questions,createdAt,updatedAt);
        }

    }

}