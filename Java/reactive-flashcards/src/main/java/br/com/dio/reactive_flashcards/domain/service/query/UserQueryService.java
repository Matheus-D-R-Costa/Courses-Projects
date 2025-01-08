package br.com.dio.reactive_flashcards.domain.service.query;

import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import br.com.dio.reactive_flashcards.domain.exception.NotFoundException;
import br.com.dio.reactive_flashcards.domain.repository.UserRepository;
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

    public Mono<UserDocument> findById(final String ID) {
        return REPOSITORY.findById(ID)
                .doFirst(() -> log.info("==== try to find user with id {}", ID))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params(ID).getMessage()))));
    }

}
