package br.com.dio.reactive_flashcards.domain.dto;

import br.com.dio.reactive_flashcards.domain.document.DeckDocument;
import br.com.dio.reactive_flashcards.domain.document.Question;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record MailMessageDto(String destination,
                             String subject,
                             String template,
                             Map<String, Object> variables) {

    public static MailMessageDtoBuilder builder() {
        return new MailMessageDtoBuilder();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailMessageDtoBuilder {

        private String destination;
        private String subject;
        private Map<String, Object> variables = new HashMap<>();

        public MailMessageDtoBuilder destination(final String DESTINATION) {
            this.destination = DESTINATION;
            return this;
        }

        public MailMessageDtoBuilder subject(final String SUBJECT) {
            this.subject = SUBJECT;
            return this;
        }

        public MailMessageDtoBuilder username(final String USERNAME) {
            return variables("username", USERNAME);
        }

        public MailMessageDtoBuilder deck(final DeckDocument DECK) {
            return variables("deck", DECK);
        }

        public MailMessageDtoBuilder questions(final List<Question> QUESTIONS) {
            QUESTIONS.sort(Comparator.comparing(Question::answeredIn));
            return variables("questions", QUESTIONS);
        }

        private MailMessageDtoBuilder variables(final String KEY, final Object VALUE) {
            this.variables.put(KEY, VALUE);
            return this;
        }

        public MailMessageDto build() {
            return new MailMessageDto(destination, subject, "mail/studyResult", variables);
        }

    }

}
