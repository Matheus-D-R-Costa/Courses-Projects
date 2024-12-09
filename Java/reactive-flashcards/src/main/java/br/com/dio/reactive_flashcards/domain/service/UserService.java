package br.com.dio.reactive_flashcards.domain.service;

import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import br.com.dio.reactive_flashcards.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository repository;

    public Mono<UserDocument> create(final UserDocument document) {
        return repository.save(document)
                .doFirst(() -> log.info("==== try to save a follow document {}", document));
    }

}
