package br.com.dio.reactive_flashcards.api.controller;

import br.com.dio.reactive_flashcards.api.controller.request.DeckRequest;
import br.com.dio.reactive_flashcards.api.controller.response.DeckResponse;
import br.com.dio.reactive_flashcards.api.mapper.DeckMapper;
import br.com.dio.reactive_flashcards.core.validation.MongoId;
import br.com.dio.reactive_flashcards.domain.service.DeckService;
import br.com.dio.reactive_flashcards.domain.service.query.DeckQueryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping("decks")
public class DeckController {

    private final DeckService SERVICE;
    private final DeckQueryService QUERY_SERVICE;

    private final DeckMapper MAPPER;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<DeckResponse> create(@Valid @RequestBody final DeckRequest REQUEST) {
        return SERVICE.create(MAPPER.toDocument(REQUEST))
                .doFirst(() -> log.info("==== Creating a deck with the follow info {}", REQUEST))
                .map(MAPPER::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Flux<DeckResponse> findAll() {
        return QUERY_SERVICE.findAll()
                .doFirst(() -> log.info("==== Finding all decks"))
                .map(MAPPER::toResponse);

    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{ID}")
    public Mono<DeckResponse> findById(@PathVariable @Valid @MongoId(message = "{deck.neededId}") final String ID) {
        return QUERY_SERVICE.findById(ID)
                .doFirst(() -> log.info("==== Finding a deck with follow id {}", ID))
                .map(MAPPER::toResponse);
    }

}
