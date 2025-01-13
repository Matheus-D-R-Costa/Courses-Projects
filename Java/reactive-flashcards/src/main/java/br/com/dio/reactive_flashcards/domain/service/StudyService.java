package br.com.dio.reactive_flashcards.domain.service;

import br.com.dio.reactive_flashcards.domain.document.Card;
import br.com.dio.reactive_flashcards.domain.document.Question;
import br.com.dio.reactive_flashcards.domain.document.StudyCard;
import br.com.dio.reactive_flashcards.domain.document.StudyDocument;
import br.com.dio.reactive_flashcards.domain.dto.QuestionDto;
import br.com.dio.reactive_flashcards.domain.dto.StudyDto;
import br.com.dio.reactive_flashcards.domain.exception.DeckPendingException;
import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import br.com.dio.reactive_flashcards.domain.mapper.StudyDomainMapper;
import br.com.dio.reactive_flashcards.domain.repository.StudyRepository;
import br.com.dio.reactive_flashcards.domain.service.query.DeckQueryService;
import br.com.dio.reactive_flashcards.domain.service.query.StudyQueryService;
import br.com.dio.reactive_flashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.*;

@Service
@Slf4j
@AllArgsConstructor
public class StudyService {

    private final StudyRepository REPOSITORY;
    private final StudyQueryService QUERY_SERVICE;
    private final UserQueryService USER_QUERY_SERVICE;
    private final DeckQueryService DECK_QUERY_SERVICE;
    private final StudyDomainMapper MAPPER;
    private final Random RANDOM = new Random();

    public Mono<StudyDocument> start(final StudyDocument DOCUMENT) {
        return verifyStudy(DOCUMENT)
                .then(Mono.defer(() -> USER_QUERY_SERVICE.findById(DOCUMENT.userId())))
                .flatMap(user -> DECK_QUERY_SERVICE.findById(DOCUMENT.studyDeck().deckId()))
                .flatMap(deck -> fillDeckStudyCards(DOCUMENT, deck.cards()))
                .map(study -> study.toBuilder()
                        .question(generateRandomQuestion(study.studyDeck().cards()))
                        .build())
                .flatMap(REPOSITORY::save)
                .doOnSuccess(study -> log.info("==== the follow study was saved {}", study));
    }

    private Mono<Void> verifyStudy(final StudyDocument DOCUMENT) {
        return QUERY_SERVICE
                .findPendingStudyByUserIdAndDeckId(DOCUMENT.userId(), DOCUMENT.studyDeck().deckId())
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

    private Question generateRandomQuestion(final Set<StudyCard> CARDS) {
        log.info("==== Generating a random question");
        var cards = new ArrayList<>(CARDS);
        var position = RANDOM.nextInt(cards.size());
        return MAPPER.toQuestion(cards.get(position));
    }

    public Mono<StudyDocument> answer(final String ID, final String ANSWER) {
        return QUERY_SERVICE.findById(ID)
                .flatMap(QUERY_SERVICE::verifyIfIsFinished)
                .map(study -> MAPPER.answer(study, ANSWER))
                .zipWhen(this::getNextQuestionPossibilities)
                .map(tuple -> MAPPER.toDto(tuple.getT1(), tuple.getT2()))
                .flatMap(this::setNewQuestion)
                .map(MAPPER::toDocument)
                .flatMap(REPOSITORY::save)
                .doFirst(() -> log.info("==== saving answer and next question if have one"));

    }

    private Mono<List<String>> getNextQuestionPossibilities(final StudyDocument DOCUMENT) {
        return Flux.fromIterable(DOCUMENT.studyDeck().cards())
                .doFirst(() -> log.info("==== Getting question not used or question without right answer"))
                .map(StudyCard::front)
                .filter(asks -> DOCUMENT.questions().stream()
                        .filter(Question::isCorrect)
                        .map(Question::asked)
                        .noneMatch(q -> q.equals(asks)))
                .collectList()
                .flatMap(asks -> removeLastAsk(asks, DOCUMENT.getLastAnsweredQuestion().asked()));
    }

    private Mono<List<String>> removeLastAsk(final List<String> ASKS, final String ASKED) {
        return Mono.just(ASKS)
                .doFirst(() -> log.info("==== removing last asked question if it is not a last pending question in study"))
                .filter(a -> a.size() == 1)
                .switchIfEmpty(Mono.defer(() -> Mono.just(ASKS.stream()
                        .filter(a -> !a.equals(ASKED))
                        .collect(Collectors.toList()))));
    }

    private Mono<StudyDto> setNewQuestion(final StudyDto DTO) {
        return Mono.just(DTO.hasAnyAnswer())
                .filter(BooleanUtils::isTrue)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(STUDY_QUESTION_NOT_FOUND
                        .params(DTO.id())
                        .getMessage()))))
                .flatMap(hasAnyAnswer -> generateNextQuestion(DTO))
                .map(question -> DTO.toBuilder().question(question).build())
                .onErrorResume(NotFoundException.class, e -> Mono.just(DTO));
    }

    private Mono<QuestionDto> generateNextQuestion(final StudyDto DTO) {
        return Mono.just(DTO.remainAsks()
                        .get(RANDOM.nextInt(DTO.remainAsks().size())))
                .doFirst(() -> log.info("==== select next random question"))
                .map(ask -> DTO.studyDeck()
                        .cards()
                        .stream()
                        .filter(card -> card.front().equals(ask))
                        .map(MAPPER::toQuestionDto)
                        .findFirst()
                        .orElseThrow());
    }

}
