package br.com.dio.reactive_flashcards.domain.service;

import br.com.dio.reactive_flashcards.domain.document.DeckDocument;
import br.com.dio.reactive_flashcards.domain.mapper.DeckDomainMapper;
import br.com.dio.reactive_flashcards.domain.repository.DeckRepository;
import br.com.dio.reactive_flashcards.domain.service.query.DeckRestQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class DeckService {

    private final DeckRepository REPOSITORY;
    private final DeckRestQueryService REST_QUERY_SERVICE;
    private final DeckDomainMapper MAPPER;

    public Mono<DeckDocument> create(final DeckDocument DOCUMENT) {
        return REPOSITORY.save(DOCUMENT)
                .doFirst(() -> log.info("==== try to save a deck with a follow info {}", DOCUMENT));
    }

    public Mono<Void> sync() {
        return Mono.empty()
                .onTerminateDetach()
                .doOnSuccess(o -> backgroundSync())
                .then();
    }

    private void backgroundSync() {
        REST_QUERY_SERVICE.getDecks()
                .map(MAPPER::toDocument)
                .flatMap(REPOSITORY::save)
                .then().subscribe();
    }

}