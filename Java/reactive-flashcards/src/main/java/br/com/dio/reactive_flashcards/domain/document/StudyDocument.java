package br.com.dio.reactive_flashcards.domain.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Document(collection = "studies")
public record StudyDocument(@Id
                            String id,

                            @Field("user_id")
                            String userId,

                            @Field("study_deck")
                            StudyDeck studyDeck,

                            List<Question> questions,
                            Boolean completed,

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
        return new StudyDocumentBuilder(id, userId, studyDeck, questions, createdAt, updatedAt);
    }

    public Question getLastPendingQuestion() {
        return questions.stream()
                .filter(q -> Objects.isNull(q.answeredIn())).findFirst().orElseThrow();
    }

    public Question getLastAnsweredQuestion() {
        return questions.stream()
                .filter(q -> Objects.nonNull(q.answeredIn()))
                .max(Comparator.comparing(Question::answeredIn))
                .orElseThrow();
    }

    public static class StudyDocumentBuilder {

        private String id;
        private String userId;
        private StudyDeck studyDeck;
        private List<Question> questions  = new ArrayList<>();
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public StudyDocumentBuilder() { }

        public StudyDocumentBuilder(
                String id,
                String userId,
                StudyDeck studyDeck,
                List<Question> questions,
                OffsetDateTime createdAt,
                OffsetDateTime updatedAt) {

            this.id = id;
            this.userId = userId;
            this.studyDeck = studyDeck;
            this.questions = questions;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;

        }


        public StudyDocumentBuilder id(final String ID) {
            this.id = ID;
            return this;
        }

        public StudyDocumentBuilder userId(final String USER_ID) {
            this.userId = USER_ID;
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
            var rightQuestions = questions.stream()
                    .filter(Question::isCorrect)
                    .toList();
            var completed = rightQuestions.size() == studyDeck.cards().size();

            return new StudyDocument(id, userId, studyDeck, questions, completed, createdAt, updatedAt);
        }

    }

}