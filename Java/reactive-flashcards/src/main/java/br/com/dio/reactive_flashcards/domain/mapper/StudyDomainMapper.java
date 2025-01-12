package br.com.dio.reactive_flashcards.domain.mapper;

import br.com.dio.reactive_flashcards.domain.document.Card;
import br.com.dio.reactive_flashcards.domain.document.Question;
import br.com.dio.reactive_flashcards.domain.document.StudyCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface StudyDomainMapper {

    StudyCard toStudyCard(final Card card);

    default Question generateRandomQuestion(Set<StudyCard> cards) {
        List<StudyCard> values = new ArrayList<>(cards);
        Random random = new Random();

        int position = random.nextInt(values.size());
        return toQuestion(values.get(position));

    }

    @Mapping(target = "asked", source = "front")
    @Mapping(target = "askedIn", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "answered", ignore = true)
    @Mapping(target = "answeredIn", ignore = true)
    @Mapping(target = "expected", source = "back")
    Question toQuestion(final StudyCard card);

}
