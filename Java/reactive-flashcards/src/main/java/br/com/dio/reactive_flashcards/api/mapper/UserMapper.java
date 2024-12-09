package br.com.dio.reactive_flashcards.api.mapper;

import br.com.dio.reactive_flashcards.api.controller.request.UserRequest;
import br.com.dio.reactive_flashcards.api.controller.response.UserResponse;
import br.com.dio.reactive_flashcards.domain.document.UserDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDocument toDocument(final UserRequest userRequest);
    UserResponse toResponse(final UserDocument document);


}
