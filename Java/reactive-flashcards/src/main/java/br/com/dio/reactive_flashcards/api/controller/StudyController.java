package br.com.dio.reactive_flashcards.api.controller;

import br.com.dio.reactive_flashcards.api.controller.request.StudyRequest;
import br.com.dio.reactive_flashcards.api.controller.response.QuestionResponse;
import br.com.dio.reactive_flashcards.api.mapper.StudyMapper;
import br.com.dio.reactive_flashcards.core.validation.MongoId;
import br.com.dio.reactive_flashcards.domain.service.StudyService;
import br.com.dio.reactive_flashcards.domain.service.query.StudyQueryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping("studies")
public class StudyController {

    private final StudyService SERVICE;
    private final StudyQueryService QUERY_SERVICE;

    private final StudyMapper MAPPER;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<QuestionResponse> start(@Valid @RequestBody final StudyRequest REQUEST) {
        return SERVICE.start(MAPPER.toDocument(REQUEST))
                .doFirst(() -> log.info("==== try to create a study with follow request {}", REQUEST))
                .map(document -> MAPPER.toResponse(document.getLastPendingQuestion(), document.id()));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{ID}")
    public Mono<QuestionResponse> getCurrentQuestion(@Valid @PathVariable @MongoId(message = "{study.neededId}") final String ID) {
        return QUERY_SERVICE.getLastPendingQuestion(ID)
                .doFirst(() -> log.info("==== try to get the next question available in study {}", ID))
                .map(question -> MAPPER.toResponse(question, ID));
    }

}
