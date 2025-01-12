package br.com.dio.reactive_flashcards.domain.service;

import br.com.dio.reactive_flashcards.domain.document.Card;
import br.com.dio.reactive_flashcards.domain.document.StudyDocument;
import br.com.dio.reactive_flashcards.domain.exception.DeckPendingException;
import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import br.com.dio.reactive_flashcards.domain.mapper.StudyDomainMapper;
import br.com.dio.reactive_flashcards.domain.repository.StudyRepository;
import br.com.dio.reactive_flashcards.domain.service.query.DeckQueryService;
import br.com.dio.reactive_flashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.DECK_PENDING;

@Service
@Slf4j
@AllArgsConstructor
public class StudyService {

    private final StudyRepository REPOSITORY;
    private final StudyRepository QUERY_SERVICE;
    private final UserQueryService USER_QUERY_SERVICE;
    private final DeckQueryService DECK_QUERY_SERVICE;
    private final StudyDomainMapper MAPPER;

    public Mono<StudyDocument> start(final StudyDocument DOCUMENT) {
        return verifyStudy(DOCUMENT)
                .then(USER_QUERY_SERVICE.findById(DOCUMENT.userId()))
                .flatMap(user -> DECK_QUERY_SERVICE.findById(DOCUMENT.studyDeck().deckId()))
                .flatMap(deck -> fillDeckStudyCards(DOCUMENT, deck.cards()))
                .map(study -> study.toBuilder()
                        .question(MAPPER.generateRandomQuestion(study.studyDeck().cards()))
                        .build())
                .doFirst(() -> log.info("==== try to generate a first random question"))
                .flatMap(REPOSITORY::save)
                .doOnSuccess(study -> log.info("==== the follow study was saved {}", study));
    }

    private Mono<Void> verifyStudy(final StudyDocument DOCUMENT) {
        return QUERY_SERVICE.findByUserIdAndIsCompleteFalseAndStudyDeck_DeckId(DOCUMENT.userId(), DOCUMENT.studyDeck().deckId())
                .flatMap(study -> Mono.defer(() -> Mono.error(new DeckPendingException(DECK_PENDING
                                .params(DOCUMENT.userId(), DOCUMENT.studyDeck().deckId()).getMessage())))
                        .onErrorResume(NotFoundException.class, e -> Mono.empty())
                        .then());
    }

    private Mono<StudyDocument> fillDeckStudyCards(final StudyDocument DOCUMENT, final Set<Card> CARDS) {
        return Flux.fromIterable(CARDS)
                .doFirst(() -> log.info("==== try to coping cards to new study"))
                .map(MAPPER::toStudyCard)
                .collectList()
                .map(studyCards -> DOCUMENT.studyDeck().toBuilder().cards(Set.copyOf(studyCards)).build())
                .map(studyDeck -> DOCUMENT.toBuilder().studyDeck(studyDeck).build());
    }

}
