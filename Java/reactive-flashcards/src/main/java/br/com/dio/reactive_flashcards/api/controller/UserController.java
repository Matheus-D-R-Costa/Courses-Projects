package br.com.dio.reactive_flashcards.api.controller;

import br.com.dio.reactive_flashcards.api.controller.request.UserPageRequest;
import br.com.dio.reactive_flashcards.api.controller.request.UserRequest;
import br.com.dio.reactive_flashcards.api.controller.response.UserPageResponse;
import br.com.dio.reactive_flashcards.api.controller.response.UserResponse;
import br.com.dio.reactive_flashcards.api.mapper.UserMapper;
import br.com.dio.reactive_flashcards.core.validation.MongoId;
import br.com.dio.reactive_flashcards.domain.service.UserService;
import br.com.dio.reactive_flashcards.domain.service.query.UserQueryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping("users")
public class UserController {

    private final UserService SERVICE;
    private final UserQueryService QUERY_SERVICE;

    private final UserMapper MAPPER;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<UserResponse> create(@Valid @RequestBody final UserRequest REQUEST) {
        return SERVICE.create(MAPPER.toDocument(REQUEST))
                .doFirst(() -> log.info("==== Creating a user with followed data {}", REQUEST))
                .map(MAPPER::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Mono<UserPageResponse> findOnDemand(@Valid final UserPageRequest REQUEST) {
        return QUERY_SERVICE.findOnDemand(REQUEST)
                .doFirst(() -> log.info("==== Finding users on demand with follow request {}", REQUEST))
                .map(userPageDto -> MAPPER.toResponse(userPageDto, REQUEST.limit()));
    }


    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{ID}")
    public Mono<UserResponse> findById(@PathVariable @Valid @MongoId(message = "{user.neededId}") final String ID) {
        return QUERY_SERVICE.findById(ID)
                .doFirst(() -> log.info("==== Finding a user with followed id {}", ID))
                .map(MAPPER::toResponse);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "{ID}")
    public Mono<UserResponse> update(@PathVariable @Valid @MongoId(message = "{user.neededId}") final String ID,
                                     @Valid @RequestBody final UserRequest REQUEST) {
        return SERVICE.update(MAPPER.toDocument(REQUEST, ID))
                .doFirst(() -> log.info("==== Updating a user with followed data [body: {}, id: {}]", REQUEST, ID))
                .map(MAPPER::toResponse);
    }

    @DeleteMapping(value = "{ID}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable @Valid @MongoId(message = "{user.neededId}") final String ID) {
        return SERVICE.delete(ID)
                .doFirst(() -> log.info("==== deleting a user with followed id {}", ID));
    }

}
