package br.com.dio.reactive_flashcards.domain.service;

import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import br.com.dio.reactive_flashcards.domain.exception.EmailAlreadyUsedException;
import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import br.com.dio.reactive_flashcards.domain.repository.UserRepository;
import br.com.dio.reactive_flashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.dio.reactive_flashcards.domain.exception.BaseErrorMessage.EMAIL_ALREADY_USED;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository REPOSITORY;
    private final UserQueryService QUERY_SERVICE;

    public Mono<UserDocument> create(final UserDocument DOCUMENT) {
        return QUERY_SERVICE.findByEmail(DOCUMENT.email())
                .filter(Objects::isNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new EmailAlreadyUsedException(EMAIL_ALREADY_USED
                        .params(DOCUMENT.email()).getMessage()))))
                .onErrorResume(NotFoundException.class, e -> REPOSITORY.save(DOCUMENT))
                .doFirst(() -> log.info("==== try to save a user followed info {}", DOCUMENT));
    }

    public Mono<UserDocument> update(final UserDocument DOCUMENT) {
        return verifyEmail(DOCUMENT).then(QUERY_SERVICE.findById(DOCUMENT.id())
                .map(user -> DOCUMENT.toBuilder()
                        .createdAt(user.createdAt())
                        .updatedAt(user.updatedAt())
                        .build())
                .flatMap(REPOSITORY::save)
                .doFirst(() -> log.info("==== try to update a user with follow info {}", DOCUMENT)));
    }

    private Mono<Void> verifyEmail(final UserDocument DOCUMENT) {
        return QUERY_SERVICE.findByEmail(DOCUMENT.email())
                .filter(stored -> stored.id().equals(DOCUMENT.id()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new EmailAlreadyUsedException(EMAIL_ALREADY_USED
                        .params(DOCUMENT.email()).getMessage()))))
                .onErrorResume(NotFoundException.class, e -> Mono.empty())
                .then();

    }

    public Mono<Void> delete(final String ID) {
        return QUERY_SERVICE.findById(ID)
                .flatMap(REPOSITORY::delete)
                .doFirst(() -> log.info("==== trying delete a user with follow id {}", ID));
    }

}
