package br.com.dio.reactive_flashcards.domain.service.query;

import br.com.dio.reactive_flashcards.domain.document.DeckDocument;
import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import br.com.dio.reactive_flashcards.domain.repository.DeckRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.DECK_NOT_FOUND;

@Service
@Slf4j
@AllArgsConstructor
public class DeckQueryService {

    private final DeckRepository REPOSITORY;

    public Mono<DeckDocument> findById(final String ID) {
        return REPOSITORY.findById(ID)
                .doFirst(() -> log.info("==== try to find a deck with id {}", ID))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(DECK_NOT_FOUND.params(ID).getMessage()))));
    }

    public Flux<DeckDocument> findAll() {
        return REPOSITORY.findAll()
                .doFirst(() -> log.info("==== try to find all decks"));

    }

}
