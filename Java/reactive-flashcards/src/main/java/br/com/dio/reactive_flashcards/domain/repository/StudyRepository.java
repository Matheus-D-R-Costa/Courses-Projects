package br.com.dio.reactive_flashcards.domain.repository;

import br.com.dio.reactive_flashcards.domain.document.StudyDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StudyRepository extends ReactiveMongoRepository<StudyDocument, String> {

    Mono<StudyDocument> findByUserIdAndStudyDeck_DeckIdAndCompletedFalse(final String USER_ID, final String DECK_ID);

}
