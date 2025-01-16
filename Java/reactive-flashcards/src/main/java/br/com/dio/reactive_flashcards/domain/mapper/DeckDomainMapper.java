package br.com.dio.reactive_flashcards.domain.mapper;

import br.com.dio.reactive_flashcards.domain.document.Card;
import br.com.dio.reactive_flashcards.domain.document.DeckDocument;
import br.com.dio.reactive_flashcards.domain.dto.CardRestDto;
import br.com.dio.reactive_flashcards.domain.dto.DeckRestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
public interface DeckDomainMapper {

    @Mapping(target = "description", source = "info")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRestDto dto);

    @Mapping(target = "back", source = "answer")
    @Mapping(target = "front", source = "ask")
    Card toDocument(final CardRestDto dto);

}
