package br.com.dio.reactive_flashcards.domain.document;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

public record StudyDeck(@Field("deck_id")
                        String deckId,

                        Set<StudyCard> cards) {

    public static StudyDeckBuilder builder() {
        return new StudyDeckBuilder();
    }

    public StudyDeckBuilder toBuilder() {
        return new StudyDeckBuilder(deckId, cards);
    }

    public static class StudyDeckBuilder {

        private String deckId;
        private Set<StudyCard> cards = new HashSet<>();

        public StudyDeckBuilder() { }

        public StudyDeckBuilder(String deckId, Set<StudyCard> cards) {
            this.deckId = deckId;
            this.cards = cards;
        }

        public StudyDeckBuilder deckId(final String DECK_ID) {
            this.deckId = DECK_ID;
            return this;
        }

        public StudyDeckBuilder cards(final Set<StudyCard> CARDS) {
            this.cards = CARDS;
            return this;
        }

        public StudyDeck build() {
            return new StudyDeck(deckId, cards);
        }

    }

}
