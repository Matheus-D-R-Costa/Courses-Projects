package br.com.dio.reactive_flashcards.core.validation;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Slf4j
public class MongoIdValidator implements ConstraintValidator<MongoId, String> {

    @Override
    public void initialize(MongoId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String s, ConstraintValidatorContext constraintValidatorContext) {
        log.info("==== checking if {} is a valid mongoDB id", s);
        return StringUtils.isNotBlank(s) && ObjectId.isValid(s);
    }

}
