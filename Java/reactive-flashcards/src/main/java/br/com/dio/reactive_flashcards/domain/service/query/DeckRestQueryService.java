package br.com.dio.reactive_flashcards.domain.service.query;

import br.com.dio.reactive_flashcards.core.DeckApiConfig;
import br.com.dio.reactive_flashcards.domain.dto.AuthResourceDto;
import br.com.dio.reactive_flashcards.domain.dto.DeckRestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class DeckRestQueryService {

    private final WebClient CLIENT;
    private final DeckApiConfig CONFIG;
    private final Mono<AuthResourceDto> AUTH_CACHE;

    public DeckRestQueryService(final WebClient CLIENT, final DeckApiConfig CONFIG) {
        this.CLIENT = CLIENT;
        this.CONFIG = CONFIG;
        this.AUTH_CACHE = Mono.from(getAuth())
                .cache(auth ->
                        Duration.ofSeconds(auth.expiresIn() -5),
                        throwable -> Duration.ZERO,
                        () -> Duration.ZERO);
    }

    public Flux<DeckRestDto> getDecks() {
        return AUTH_CACHE
                .flatMapMany(auth -> getDecks(auth.token()));
    }

    private Flux<DeckRestDto> getDecks(final String TOKEN) {
        return CLIENT.get()
                .uri(CONFIG.getDecksUri())
                .header("token", TOKEN)
                .retrieve()
                .bodyToFlux(DeckRestDto.class);
    }

    private Mono<AuthResourceDto> getAuth() {
        return CLIENT.post()
                .uri(CONFIG.getAuthUri())
                .contentType(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AuthResourceDto.class);
    }

}
