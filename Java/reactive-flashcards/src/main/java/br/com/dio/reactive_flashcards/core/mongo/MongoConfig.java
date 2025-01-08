package br.com.dio.reactive_flashcards.core.mongo;

import br.com.dio.reactive_flashcards.core.mongo.converter.DateToOffsetDateTimeConverter;
import br.com.dio.reactive_flashcards.core.mongo.converter.OffsetDateTImeToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class MongoConfig {

    @Bean
    MongoCustomConversions mongoCustomConversions() {
        final List<Converter<?, ?>> CONVERTERS = new ArrayList<>();
        CONVERTERS.add(new OffsetDateTImeToDateConverter());
        CONVERTERS.add(new DateToOffsetDateTimeConverter());
        return new MongoCustomConversions(CONVERTERS);
    }

}
