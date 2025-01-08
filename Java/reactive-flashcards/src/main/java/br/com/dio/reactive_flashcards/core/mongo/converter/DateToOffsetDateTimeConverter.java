package br.com.dio.reactive_flashcards.core.mongo.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.util.Date;

public class DateToOffsetDateTimeConverter implements Converter<OffsetDateTime, Date> {

    @Override
    public Date convert(final OffsetDateTime SOURCE) {
        return Date.from(SOURCE.toInstant());
    }

}
