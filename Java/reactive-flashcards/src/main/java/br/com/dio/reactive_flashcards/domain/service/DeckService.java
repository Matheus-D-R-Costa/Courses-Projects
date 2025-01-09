package br.com.dio.reactive_flashcards.domain.service;

import br.com.dio.reactive_flashcards.domain.document.DeckDocument;
import br.com.dio.reactive_flashcards.domain.repository.DeckRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class DeckService {

    private final DeckRepository REPOSITORY;

    public Mono<DeckDocument> create(final DeckDocument DOCUMENT) {
        return REPOSITORY.save(DOCUMENT)
                .doFirst(() -> log.info("==== try to save a deck with a follow info {}", DOCUMENT));
    }

}
