package br.com.dio.reactive_flashcards.api.mapper;

import br.com.dio.reactive_flashcards.api.controller.request.UserRequest;
import br.com.dio.reactive_flashcards.api.controller.response.UserPageResponse;
import br.com.dio.reactive_flashcards.api.controller.response.UserResponse;
import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import br.com.dio.reactive_flashcards.domain.dto.UserPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request, final String id);

    UserResponse toResponse(final UserDocument document);

    UserPageResponse toResponse(final UserPageDto dto, final Integer limit);

}
