package br.com.dio.reactive_flashcards.domain.repository;

import br.com.dio.reactive_flashcards.domain.document.StudyDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends ReactiveMongoRepository<StudyDocument, String> {

}
