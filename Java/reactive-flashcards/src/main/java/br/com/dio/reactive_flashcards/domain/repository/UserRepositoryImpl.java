package br.com.dio.reactive_flashcards.domain.repository;

import br.com.dio.reactive_flashcards.api.controller.request.UserPageRequest;
import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl {

    public ReactiveMongoTemplate template;

    public Flux<UserDocument> findOnDemand(final UserPageRequest REQUEST) {
        return Mono.just(new Query())
                .flatMap(query -> buildWhere(query, REQUEST.sentence()))
                .map(query -> query.with(REQUEST.getSort()).skip(REQUEST.getSkip()).limit(REQUEST.limit()))
                .doFirst(() -> log.info("==== Find users on demand with follow request {}", REQUEST))
                .flatMapMany(query -> template.find(query, UserDocument.class));
    }

    public Mono<Long> count(final UserPageRequest REQUEST) {
        return Mono.just(new Query())
                .flatMap(query -> buildWhere(query, REQUEST.sentence()))
                .doFirst(() -> log.info("==== Counting users with follow request {}", REQUEST))
                .flatMap(query -> template.count(query, UserDocument.class));
    }

    private Mono<Query> buildWhere(final Query QUERY, final String SENTENCE) {
       return Mono.just(QUERY)
               .filter(q -> StringUtils.isBlank(SENTENCE))
               .switchIfEmpty(Mono.defer(() -> Mono.just(QUERY))
                       .flatMapIterable(q -> List.of("name", "email"))
                       .map(field -> Criteria.where(field).regex(SENTENCE, "i"))
                       .collectList()
                       .map(criteriaList -> setWhereClause(QUERY, criteriaList)));
    }

    private Query setWhereClause(final Query QUERY, final List<Criteria> LIST) {
        var whereClause = new Criteria();
        whereClause.orOperator(LIST);
        return QUERY.addCriteria(whereClause);
    }

}
