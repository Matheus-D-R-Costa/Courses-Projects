package br.com.dio.reactive_flashcards.domain.service.query;

import br.com.dio.reactive_flashcards.domain.document.Question;
import br.com.dio.reactive_flashcards.domain.document.StudyDocument;
import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import br.com.dio.reactive_flashcards.domain.repository.StudyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.*;

@Service
@Slf4j
@AllArgsConstructor
public class StudyQueryService {

    private final StudyRepository REPOSITORY;

    public Mono<Question> getLastPendingQuestion(final String ID) {
        return findById(ID)
                .filter(study -> BooleanUtils.isFalse(study.isComplete()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(STUDY_QUESTION_NOT_FOUND.params(ID).getMessage()))))
                .flatMapMany(study -> Flux.fromIterable(study.questions()))
                .filter(Question::isAnswered)
                .doFirst(() -> log.info("==== getting the current pending question in study {}", ID))
                .single();
    }

    public Mono<StudyDocument> findById(final String ID) {
        return REPOSITORY.findById(ID)
                .doFirst(() -> log.info("==== try to find study with id {}", ID))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(STUDY_NOT_FOUND.params(ID).getMessage()))));
    }

    public Mono<StudyDocument> findPendingStudyByUserIdAndDeckId(final String USER_ID, final String DECK_ID) {
        return REPOSITORY.findByUserIdAndIsCompleteFalseAndStudyDeck_DeckId(USER_ID, DECK_ID)
                .doFirst(() -> log.info("=== try to get pending study with userId {} and deckId {}", USER_ID, DECK_ID))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(STUDY_DECK_NOT_FOUND.params(USER_ID, DECK_ID).getMessage()))));
    }

}
