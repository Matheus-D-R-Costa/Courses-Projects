package br.com.dio.reactive_flashcards.domain.service;

import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import br.com.dio.reactive_flashcards.domain.repository.UserRepository;
import br.com.dio.reactive_flashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository REPOSITORY;
    private final UserQueryService QUERY_SERVICE;

    public Mono<UserDocument> create(final UserDocument DOCUMENT) {
        return REPOSITORY.save(DOCUMENT)
                .doFirst(() -> log.info("==== try to save a user followed info {}", DOCUMENT));
    }

    public Mono<UserDocument> update(final UserDocument DOCUMENT) {
        return QUERY_SERVICE.findById(DOCUMENT.id())
                .map(user -> DOCUMENT.toBuilder()
                        .createdAt(user.createdAt())
                        .updatedAt(user.updatedAt())
                        .build())
                .flatMap(REPOSITORY::save)
                .doFirst(() -> log.info("==== try to update a user with followed info {}", DOCUMENT));
    }

    public Mono<Void> delete(final String ID) {
        return QUERY_SERVICE.findById(ID)
                .flatMap(REPOSITORY::delete)
                .doFirst(() -> log.info("==== trying delete a user with followed id {}", ID));
    }


}
