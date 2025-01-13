package br.com.dio.reactive_flashcards.domain.mapper;

import br.com.dio.reactive_flashcards.domain.document.Card;
import br.com.dio.reactive_flashcards.domain.document.Question;
import br.com.dio.reactive_flashcards.domain.document.StudyCard;
import br.com.dio.reactive_flashcards.domain.document.StudyDocument;
import br.com.dio.reactive_flashcards.domain.dto.QuestionDto;
import br.com.dio.reactive_flashcards.domain.dto.StudyCardDto;
import br.com.dio.reactive_flashcards.domain.dto.StudyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudyDomainMapper {

    StudyCard toStudyCard(final Card card);

    @Mapping(target = "asked", source = "front")
    @Mapping(target = "answered", ignore = true)
    @Mapping(target = "expected", source = "back")
    Question toQuestion(final StudyCard card);

    @Mapping(target = "asked", source = "front")
    @Mapping(target = "answered", ignore = true)
    @Mapping(target = "expected", source = "back")
    QuestionDto toQuestionDto(final StudyCardDto card);

    default StudyDocument answer(final StudyDocument document, final String answer) {
        Question currentQuestion = document.getLastPendingQuestion();
        List<Question> questions = document.questions();
        int currentIndexQuestion = questions.indexOf(currentQuestion);
        currentQuestion = currentQuestion.toBuilder().answered(answer).build();
        questions.set(currentIndexQuestion, currentQuestion);
        return document.toBuilder().questions(questions).build();
    }

    @Mapping(target = "question", ignore = true)
    StudyDto toDto(final StudyDocument document, final List<String> remainAsks);

    @Mapping(target = "question", ignore = true)
    StudyDocument toDocument(final StudyDto dto);
}
