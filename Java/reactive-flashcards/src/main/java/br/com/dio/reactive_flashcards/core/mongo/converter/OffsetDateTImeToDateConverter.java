package br.com.dio.reactive_flashcards.core.mongo.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

public class OffsetDateTImeToDateConverter implements Converter<Date, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(Date SOURCE) {
        return OffsetDateTime.ofInstant(SOURCE.toInstant(), ZoneId.systemDefault());
    }

}
