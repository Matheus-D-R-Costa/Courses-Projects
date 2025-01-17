package br.com.dio.reactive_flashcards.domain.service.query;

import br.com.dio.reactive_flashcards.api.controller.request.UserPageRequest;
import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import br.com.dio.reactive_flashcards.domain.dto.UserPageDto;
import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import br.com.dio.reactive_flashcards.domain.repository.UserRepository;
import br.com.dio.reactive_flashcards.domain.repository.UserRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.USER_NOT_FOUND;

@Service
@Slf4j
@AllArgsConstructor
public class UserQueryService {

    private final UserRepository REPOSITORY;
    private final UserRepositoryImpl REPOSITORY_IMPL;

    public Mono<UserDocument> findById(final String ID) {
        return REPOSITORY.findById(ID)
                .doFirst(() -> log.info("==== try to find user with id {}", ID))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params("id", ID).getMessage()))));
    }

    public Mono<UserDocument> findByEmail(final String EMAIL) {
        return REPOSITORY.findByEmail(EMAIL)
                .doFirst(() -> log.info("==== try to find user with email {}", EMAIL))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params("email", EMAIL).getMessage()))));
    }

    public Mono<UserPageDto> findOnDemand(final UserPageRequest REQUEST) {
        return REPOSITORY_IMPL.findOnDemand(REQUEST)
                .collectList()
                .zipWhen(documents -> REPOSITORY_IMPL.count(REQUEST))
                .map(tuple -> UserPageDto.builder()
                        .limit(REQUEST.limit())
                        .currentPage(REQUEST.page())
                        .totalItems(tuple.getT2())
                        .content(tuple.getT1())
                        .build());
    }

}
