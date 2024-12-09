package br.com.dio.reactive_flashcards.api.controller;

import br.com.dio.reactive_flashcards.api.controller.request.UserRequest;
import br.com.dio.reactive_flashcards.api.controller.response.UserResponse;
import br.com.dio.reactive_flashcards.api.mapper.UserMapper;
import br.com.dio.reactive_flashcards.domain.service.UserService;
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
@RequestMapping("users")
public class UserController {

    private final UserService service;
    private final UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<UserResponse> create(@Valid @RequestBody final UserRequest request) {
        return service.create(userMapper.toDocument(request))
                .doFirst(() -> log.info("==== Creating a user with fallow data {}", request))
                .map(userMapper::toResponse);
    }


}
